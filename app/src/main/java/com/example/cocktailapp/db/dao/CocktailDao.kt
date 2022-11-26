package com.example.cocktailapp.db.dao

import androidx.room.*
import com.example.cocktailapp.db.entity.DrinkEntity

@Dao
interface CocktailDao {

    @Query("SELECT * FROM cocktails")
    fun getAllCocktails(): List<DrinkEntity>

    @Query("SELECT * FROM cocktails WHERE idDrink=:idDrink")
    suspend fun getCocktailById(idDrink: String): DrinkEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(drinks: List<DrinkEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(drink: DrinkEntity)
}