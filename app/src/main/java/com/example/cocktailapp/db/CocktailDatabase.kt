package com.example.cocktailapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cocktailapp.db.dao.CocktailDao
import com.example.cocktailapp.db.entity.DrinkEntity

@Database(entities = [DrinkEntity::class], version = 1)
abstract class CocktailDatabase: RoomDatabase() {
    abstract  fun cocktailDao():CocktailDao
}