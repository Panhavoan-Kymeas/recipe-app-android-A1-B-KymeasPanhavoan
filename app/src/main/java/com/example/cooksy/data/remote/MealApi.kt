package com.example.cooksy.data.remote

import com.example.cooksy.data.model.Category
import com.example.cooksy.data.model.Meal
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MealApi {

    /**
     * Get all meals or filter by category or area.
     * If category or area is null, it fetches all meals.
     */
    @GET("meals")
    suspend fun getMeals(
        @Header("X-DB-NAME") dbName: String,
        @Query("category") category: String? = null,
        @Query("area") area: String? = null
    ): List<Meal>

    /**
     * Get a single meal by its ID.
     */
    @GET("meals/{id}")
    suspend fun getMealById(
        @Header("X-DB-NAME") dbName: String,
        @Path("id") id: String
    ): Meal

    /**
     * Get all categories.
     */
    @GET("categories")
    suspend fun getCategories(
        @Header("X-DB-NAME") dbName: String
    ): List<Category>

}