package com.example.cocktailapp.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cocktailapp.db.entity.DrinkEntity
import com.example.cocktailapp.repository.DrinkRepository
import com.example.cocktailapp.viewmodel.DrinkViewModel

class DrinkFactory(  private val drinkRepository: DrinkRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DrinkViewModel(drinkRepository) as T
    }
}
