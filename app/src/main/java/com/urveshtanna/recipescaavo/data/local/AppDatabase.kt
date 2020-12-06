package com.urveshtanna.recipescaavo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.urveshtanna.recipescaavo.data.local.dao.LocalDAO
import com.urveshtanna.recipescaavo.data.model.Recipe

@Database(entities = [Recipe::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recipeDao(): LocalDAO

    companion object {

        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "recipe_db")
                .fallbackToDestructiveMigration()
                .build()

    }

}