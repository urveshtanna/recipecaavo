package com.urveshtanna.recipescaavo.data.remote

import javax.inject.Inject

class RecipeRemoteDataSource @Inject constructor(private val recipeAPIService: RecipeAPIService) :
    BaseDataSource() {

    suspend fun getRecipes() = getResult { recipeAPIService.getRecipes() }

}