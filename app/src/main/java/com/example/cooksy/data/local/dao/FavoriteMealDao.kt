package com.example.cooksy.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.cooksy.data.local.entity.FavoriteMealEntity
import com.example.cooksy.data.local.entity.IngredientEntity
import kotlinx.coroutines.flow.Flow
import com.example.cooksy.data.local.entity.FavoriteMealWithIngredients

@Dao
interface FavoriteMealDao {

    @Transaction
    @Query("SELECT * FROM favorite_meals")
    fun getAllFavoriteMeals(): Flow<List<FavoriteMealWithIngredients>>

    @Transaction
    @Query("SELECT * FROM favorite_meals WHERE id = :id")
    suspend fun getFavoriteMealById(id: String): FavoriteMealWithIngredients?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: FavoriteMealEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredients(ingredients: List<IngredientEntity>)

    @Delete
    suspend fun deleteMeal(meal: FavoriteMealEntity)
}
