package com.example.cocktailapp.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cocktailapp.R
import com.example.cocktailapp.activity.MainActivity
import com.example.cocktailapp.databinding.DrinkItemBinding
import com.example.cocktailapp.databinding.MIItemBinding
import com.example.cocktailapp.db.CocktailDatabase
import com.example.cocktailapp.db.entity.DrinkEntity
import com.example.cocktailapp.fragment.DrinkFragment

class MIAdapter (private val drinks:List<String?>):

    RecyclerView.Adapter<MIAdapter.DrinkViewHolder>() {
    var db: CocktailDatabase? = null

    class DrinkViewHolder(val binding: MIItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MIItemBinding.inflate(layoutInflater, parent, false)

        return DrinkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val currentDrink = drinks[position]
        holder.binding.apply {
            ingredientMeasure=currentDrink
//            strDrink = currentDrink.strDrink
//            idDrink = currentDrink.idDrink
//            ivLiked.visibility = if (currentCryptoCurrency.favourite) View.VISIBLE else View.GONE
        }
    }

    override fun getItemCount() = drinks.size

}