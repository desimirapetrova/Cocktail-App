package com.example.cocktailapp.repository

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.cocktailapp.db.dao.CocktailDao
import com.example.cocktailapp.db.entity.DrinkEntity
import com.example.cocktailapp.model.DrinkResponse
import com.example.cocktailapp.service.DrinkService
import com.example.cocktailapp.util.NetworkUtil

class DrinkRepository constructor(
    private val context: Context,
    private val drinkService: DrinkService,
    private val cocktailDao: CocktailDao
) {

    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun getDrinks(): List<DrinkEntity> {
        return try {
            // if Internet connection is available fetch countries, save them to DB and return them
            val savedinDbMarkets = cocktailDao.getAllCocktails()

            if (NetworkUtil.isConnected(context)) {
                // execute the network request synchronously in order to wait for the response and handle it
                val drinks = drinkService.getDrinks().execute().body()
                val roomDrinks = drinks?.drinks?.map { mapResponseToDbModel(it) }
                drinks?.drinks?.forEach { new ->
                    var oldDrinkDatabaseModel = savedinDbMarkets.find { it.idDrink == new.idDrink }
                    // We only know that there is one property that may be different
                    if (oldDrinkDatabaseModel != null && oldDrinkDatabaseModel.favourite != new.favourite) {
                        new.favourite = oldDrinkDatabaseModel.favourite
                    }
                }
                cocktailDao.insertAll(roomDrinks ?: return arrayListOf())

            }

            cocktailDao.getAllCocktails()
        } catch (e: Exception) {
            arrayListOf()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun getDrink(idDrink: String): DrinkEntity? {
        return try {
            // if Internet connection is available fetch countries, save them to DB and return them
            if (NetworkUtil.isConnected(context)) {
                // execute the network request synchronously in order to wait for the response and handle it
                val drinks = drinkService.getDrinkById(idDrink).execute().body()?.drinks
                val roomDrinks = drinks?.map { mapResponseToDbModel(it) }
                cocktailDao.update(roomDrinks?.get(0) ?: return null)
            }

            return cocktailDao.getCocktailById(idDrink)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updateDrink(drink: DrinkEntity) {
        cocktailDao.update(drink)
    }

    private fun mapResponseToDbModel(response: DrinkResponse): DrinkEntity {
        return DrinkEntity(
            idDrink = response.idDrink ?: "",
            strDrinkThumb = response.strDrinkThumb ?: "",
            strDrink = response.strDrink ?: "",
            favourite = false,
            strGlass = response.strGlass?:"",
            strInstructions = response.strInstructions?:"",
            strIngredient1 = response.strIngredient1?:"",
            strIngredient2 = response.strIngredient2?:"",
            strIngredient3 = response.strIngredient3?:"",
            strIngredient4 = response.strIngredient4?:"",
            strIngredient5 = response.strIngredient5?:"",
            strIngredient6 = response.strIngredient6?:"",
            strIngredient7 = response.strIngredient7?:"",
            strIngredient8 = response.strIngredient8?:"",
            strIngredient9 = response.strIngredient9?:"",
            strIngredient10 = response.strIngredient10?:"",
            strIngredient11 = response.strIngredient11?:"",
            strIngredient12 = response.strIngredient12?:"",
            strIngredient13 = response.strIngredient13?:"",
            strIngredient14 = response.strIngredient14?:"",
            strIngredient15 = response.strIngredient15?:"",
            strMeasure1 = response.strMeasure1?:"",
            strMeasure2 = response.strMeasure2?:"",
            strMeasure3 = response.strMeasure3?:"",
            strMeasure4 = response.strMeasure4?:"",
            strMeasure5 = response.strMeasure5?:"",
            strMeasure6 = response.strMeasure6?:"",
            strMeasure7 = response.strMeasure7?:"",
            strMeasure8 = response.strMeasure8?:"",
            strMeasure9 = response.strMeasure9?:"",
            strMeasure10 = response.strMeasure10?:"",
            strMeasure11 = response.strMeasure11?:"",
            strMeasure12 = response.strMeasure12?:"",
            strMeasure13 = response.strMeasure13?:"",
            strMeasure14 = response.strMeasure14?:"",
            strMeasure15 = response.strMeasure15?:"",
        )
    }
}