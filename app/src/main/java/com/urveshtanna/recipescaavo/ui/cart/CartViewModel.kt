package com.urveshtanna.recipescaavo.ui.cart

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.urveshtanna.recipescaavo.data.model.Recipe
import com.urveshtanna.recipescaavo.data.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class CartViewModel @ViewModelInject constructor(private val recipeRepository: RecipeRepository) :
    ViewModel() {

    var cartItems = recipeRepository.getCartItem()

    fun updateQuantity(recipe: Recipe) {
        viewModelScope.launch(Dispatchers.IO) {
            coroutineScope {
                recipeRepository.updateQuantity(recipe)
            }
        }
    }

}