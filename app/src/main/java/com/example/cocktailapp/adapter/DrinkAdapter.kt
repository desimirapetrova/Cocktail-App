package com.example.cocktailapp.adapter

import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cocktailapp.R
import com.example.cocktailapp.activity.MainActivity
import com.example.cocktailapp.databinding.DrinkItemBinding
import com.example.cocktailapp.db.CocktailDatabase
import com.example.cocktailapp.db.entity.DrinkEntity
import com.example.cocktailapp.fragment.DrinkFragment
import java.util.*

class DrinkAdapter (private val drinks:List<DrinkEntity>):

    RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder>() {
    var db: CocktailDatabase? = null

    class DrinkViewHolder(val binding: DrinkItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DrinkItemBinding.inflate(layoutInflater, parent, false)

        return DrinkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val currentDrink = drinks[position]
        holder.binding.apply {
            strDrink = currentDrink.strDrink
            idDrink = currentDrink.idDrink
//            ivLiked.visibility = if (currentCryptoCurrency.favourite) View.VISIBLE else View.GONE

            Glide
                .with(this.root.context)
                .load(currentDrink.strDrinkThumb)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(ivLogo)
        }

        holder.binding.root.setOnClickListener {
            (it.context as MainActivity).supportFragmentManager.commit {
                val bundle = Bundle();
                bundle.putString("drink_id", currentDrink.idDrink)
                replace(R.id.container_view, DrinkFragment::class.java, bundle)
                addToBackStack("drink_list_to_details")
            }

        }
    }

    override fun getItemCount() = drinks.size

}