package com.example.cocktailapp.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cocktailapp.R
import com.example.cocktailapp.adapter.DrinkAdapter
import com.example.cocktailapp.databinding.ActivityMainBinding
import com.example.cocktailapp.db.CocktailDatabase
import com.example.cocktailapp.factory.DrinkFactory
import com.example.cocktailapp.repository.DrinkRepository
import com.example.cocktailapp.service.DrinkService
import com.example.cocktailapp.util.NetworkUtil
import com.example.cocktailapp.viewmodel.DrinkViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var drinkService: DrinkService

    private lateinit var drinkRepository: DrinkRepository

    lateinit var drinkViewModel: DrinkViewModel

    lateinit var db: RoomDatabase

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient())
        .build()

    //    @RequiresApi(Build.VERSION_CODES.M)
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        if (!NetworkUtil.isConnected(this)) {
            Snackbar.make(
                binding.root,
                "No internet connection, information could be outdated",
                Snackbar.LENGTH_LONG
            ).show()
        }

        GlobalScope.launch {
            drinkViewModel.getDrinks()
        }
        observeData()
        setContentView(binding.root)
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        observeData()
    }

    private fun init() {
        db = Room.databaseBuilder(
            applicationContext,
            CocktailDatabase::class.java,
            "cocktails"
        ).build()
        val drinkDao = (db as CocktailDatabase).cocktailDao()

        drinkService = retrofit.create(DrinkService::class.java)
        drinkRepository =
            DrinkRepository(this, drinkService, drinkDao)
        val drinkFactory = DrinkFactory(drinkRepository)
        drinkViewModel =
            ViewModelProvider(this, drinkFactory)[DrinkViewModel::class.java]
    }

    private fun observeData() {
        GlobalScope.launch {
            drinkViewModel.drinksList.collect {
                runOnUiThread {
                    val drinks = it
                    val sortedDrinksByStrDrink = drinks.sortedBy { it.strDrink }
                    val sortedDrinksMarkets = sortedDrinksByStrDrink.sortedByDescending { it.favourite }
                    val adapter = DrinkAdapter(sortedDrinksMarkets)
                    binding.tvDrinksCountList.adapter = adapter
                    binding.tvDrinkCount.text = "Drinks: ${it.size}"

                }
            }
        }
    }
}