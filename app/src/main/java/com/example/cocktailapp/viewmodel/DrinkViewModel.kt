package com.example.cocktailapp.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.cocktailapp.db.entity.DrinkEntity
import com.example.cocktailapp.repository.DrinkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DrinkViewModel (
    private val drinkRepository: DrinkRepository
) : ViewModel() {

    private val drinksListStateFlow =
        MutableStateFlow<List<DrinkEntity>>(arrayListOf())
    private val selectedDrinkStateFlow = MutableStateFlow<DrinkEntity?>(null)
    val drinksList: StateFlow<List<DrinkEntity>> =
        drinksListStateFlow.asStateFlow()
    val selectedDrink: StateFlow<DrinkEntity?> =
        selectedDrinkStateFlow.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun getDrinks() {
        val drinks = drinkRepository.getDrinks()
        drinksListStateFlow.value = drinks
    }

    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun getDrink(idDrink: String) {
        val drink = drinkRepository.getDrink(idDrink)
        selectedDrinkStateFlow.value = drink ?: return
    }

    suspend fun updateFavourites(drink: DrinkEntity) {
        drinkRepository.updateDrink(drink)
        selectedDrinkStateFlow.value =
            selectedDrinkStateFlow.value?.copy(favourite = drink.favourite)
    }

}