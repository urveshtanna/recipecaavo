package com.urveshtanna.recipescaavo.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.urveshtanna.recipescaavo.data.model.Recipe

@Dao
interface LocalDAO {

    @Query("select * from Recipe")
    fun getAllRecipeList(): LiveData<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recipeList: List<Recipe>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe)

    @Query("DELETE FROM recipe")
    suspend fun deleteAll()

    @Query("UPDATE Recipe SET quantity=:quantity where id = :id")
    suspend fun updateRecipe(quantity: Int, id: Long)

    @Query("select * from Recipe where quantity != 0")
    fun getCartRecipes(): LiveData<List<Recipe>>

    @Query("select COUNT(quantity) FROM Recipe WHERE quantity != 0")
    suspend fun getCartCount(): Int
}