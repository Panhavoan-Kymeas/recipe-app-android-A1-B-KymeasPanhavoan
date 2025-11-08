package com.example.cooksy.di

import com.example.cooksy.data.remote.MealApi
import com.example.cooksy.data.repository.MealRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMealRepository(
        api: MealApi,
        favoriteDao: com.example.cooksy.data.local.dao.FavoriteMealDao
    ): MealRepository {
        return MealRepository(api, favoriteDao)
    }
}
