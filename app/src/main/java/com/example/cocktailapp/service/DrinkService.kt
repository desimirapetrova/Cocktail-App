package com.example.cocktailapp.service

import com.example.cocktailapp.model.CocktailDrinksResponse
import com.example.cocktailapp.model.DrinkResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DrinkService {

    @GET("filter.php?c=Cocktail")
    fun getDrinks(): Call<CocktailDrinksResponse>

    @GET("lookup.php")
    fun getDrinkById(@Query("i")  i :String): Call<CocktailDrinksResponse>
}