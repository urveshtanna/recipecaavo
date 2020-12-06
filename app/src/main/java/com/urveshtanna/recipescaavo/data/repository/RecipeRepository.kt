package com.urveshtanna.recipescaavo.data.repository

import com.urveshtanna.recipescaavo.data.local.dao.LocalDAO
import com.urveshtanna.recipescaavo.data.model.Recipe
import com.urveshtanna.recipescaavo.data.performGetOperation
import com.urveshtanna.recipescaavo.data.remote.RecipeRemoteDataSource
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val remoteDataSource: RecipeRemoteDataSource,
    private val localDataSource: LocalDAO
) {

    fun getRecipes() = performGetOperation(
        databaseQuery = {
            localDataSource.getAllRecipeList()
        },
        networkCall = {
            remoteDataSource.getRecipes()
        },
        saveCallResult = {
            localDataSource.insertAll(it)
        }
    )

    fun getCartItem() = localDataSource.getCartRecipes()

    suspend fun getCartCount() = localDataSource.getCartCount()

    suspend fun updateQuantity(recipe : Recipe) = localDataSource.updateRecipe(recipe.quantity, recipe.id)

}