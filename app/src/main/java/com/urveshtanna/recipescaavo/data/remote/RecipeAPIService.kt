package com.urveshtanna.recipescaavo.data.remote

import com.urveshtanna.recipescaavo.data.model.Recipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeAPIService {

    /**
     * Get Recipe From Server
     */
    @GET("/he-public-data/reciped9d7b8c.json")
    suspend fun getRecipes(): Response<List<Recipe>>

}