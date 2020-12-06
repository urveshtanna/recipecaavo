package com.urveshtanna.recipescaavo.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.urveshtanna.recipescaavo.data.model.Recipe
import com.urveshtanna.recipescaavo.data.repository.RecipeRepository
import com.urveshtanna.recipescaavo.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class RecipeListViewModel @ViewModelInject constructor(private val recipeRepository: RecipeRepository) :
    ViewModel() {

    var recipes = recipeRepository.getRecipes()
    var cartCount = MutableLiveData<Int>()

    fun getCartCount() {
        viewModelScope.launch(Dispatchers.IO) {
            coroutineScope {
                cartCount.postValue(recipeRepository.getCartCount())
            }
        }
    }

    fun updateQuantity(recipe: Recipe) {
        viewModelScope.launch(Dispatchers.IO) {
            coroutineScope {
                recipeRepository.updateQuantity(recipe)
                getCartCount()
            }
        }
    }

}