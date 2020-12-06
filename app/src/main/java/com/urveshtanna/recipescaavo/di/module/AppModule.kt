package com.urveshtanna.recipescaavo.di.module

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.urveshtanna.recipescaavo.data.local.AppDatabase
import com.urveshtanna.recipescaavo.data.local.dao.LocalDAO
import com.urveshtanna.recipescaavo.data.remote.RecipeAPIService
import com.urveshtanna.recipescaavo.data.remote.RecipeRemoteDataSource
import com.urveshtanna.recipescaavo.data.repository.RecipeRepository
import com.urveshtanna.recipescaavo.utils.API_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(logging)
        return builder.build()
    }

    @Provides
    fun provideRecipeAPIService(retrofit: Retrofit): RecipeAPIService =
        retrofit.create(RecipeAPIService::class.java)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideRecipeDAO(db: AppDatabase) = db.recipeDao()

    @Singleton
    @Provides
    fun provideRecipeRepository(
        remoteDataSource: RecipeRemoteDataSource,
        localDataSource: LocalDAO
    ) = RecipeRepository(remoteDataSource, localDataSource)

}