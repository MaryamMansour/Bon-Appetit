package com.example.recipe_app.di

import android.content.Context
import androidx.room.Room
import com.example.recipe_app.local.LocalSourceImp
import com.example.recipe_app.local.db.MealDataBase
import com.example.recipe_app.network.ApiClient
import com.example.recipe_app.network.ApiInterface
import com.example.recipe_app.repository.Repository
import com.example.recipe_app.repository.RepositoryImpl
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RecipeDataModule {
    @Singleton
    @Provides
    fun provideMealsDataBase(
        @ApplicationContext context: Context
    ): MealDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            MealDataBase::class.java,
            "FavMeals"
        ).build()
    }

    @Provides
    fun provideMealDao(mealDataBase: MealDataBase) = mealDataBase.mealDao()

    @Provides
    fun providePersonInfoDao(mealDataBase: MealDataBase) = mealDataBase.personinfodao()

    @Provides
    fun provideUserFavouriteDao(mealDataBase: MealDataBase) = mealDataBase.userFavouriteDao()


    @Provides
    fun provideRetrofitInstance(): Retrofit {
        val gson = GsonBuilder().serializeNulls().create()
        return  Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }
    @Provides
    fun provideRepository(localSourceImp: LocalSourceImp,apiClient: ApiClient): Repository {
        return RepositoryImpl(localSourceImp, apiClient)
    }


}