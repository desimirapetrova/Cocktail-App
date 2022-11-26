package com.example.cocktailapp.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cocktails")
data class DrinkEntity(
    @PrimaryKey
    @ColumnInfo(name = "strDrink") var strDrink: String,
    @ColumnInfo(name = "strDrinkThumb") var strDrinkThumb: String,
    @ColumnInfo(name = "idDrink") var idDrink: String,
    @ColumnInfo(name = "favourite") var favourite: Boolean=false,
    @ColumnInfo(name = "strGlass") var strGlass: String,
    @ColumnInfo(name = "strInstructions") var strInstructions: String,
    var strMeasure1: String,
    var strMeasure2: String,
    var strMeasure3: String,
    var strMeasure4: String,
    var strMeasure5: String,
    var strMeasure6: String,
    var strMeasure7: String,
    var strMeasure8: String,
    var strMeasure9: String,
    var strMeasure10: String,
    var strMeasure11: String,
    var strMeasure12: String,
    var strMeasure13: String,
    var strMeasure14: String,
    var strMeasure15: String,
    var strIngredient1: String,
    var strIngredient2: String,
    var strIngredient3: String,
    var strIngredient4: String,
    var strIngredient5: String,
    var strIngredient6: String,
    var strIngredient7: String,
    var strIngredient8: String,
    var strIngredient9: String,
    var strIngredient10: String,
    var strIngredient11: String,
    var strIngredient12: String,
    var strIngredient13: String,
    var strIngredient14: String,
    var strIngredient15: String,
)
