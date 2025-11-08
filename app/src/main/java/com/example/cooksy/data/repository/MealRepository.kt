package com.example.cooksy.data.repository

import com.example.cooksy.data.local.dao.FavoriteMealDao
import com.example.cooksy.data.local.entity.FavoriteMealEntity
import com.example.cooksy.data.local.entity.IngredientEntity
import com.example.cooksy.data.model.Ingredient
import com.example.cooksy.data.model.Meal
import com.example.cooksy.data.remote.MealApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import com.example.cooksy.BuildConfig.DB_NAME

@Singleton
class MealRepository @Inject constructor(
    private val api: MealApi,
    private val favoriteDao: FavoriteMealDao
) {

    // Variable
    private var cachedAreas: List<String>? = null

    // -----------------------------
    // Remote (API) functions
    // -----------------------------
    suspend fun getMeals(category: String? = null, area: String? = null): List<Meal> {
        return api.getMeals(DB_NAME, category, area)
    }

    suspend fun getPopularMeals(): List<Meal>{
        val meals = api.getMeals(DB_NAME)
        return meals.shuffled().take(10)
    }

    suspend fun getRandomMeal(): Meal? {
        val meals = api.getMeals(DB_NAME)
        return if (meals.isNotEmpty()) meals.random() else null
    }

    suspend fun getMealById(id: String): Meal {
        return api.getMealById(DB_NAME, id)
    }

    suspend fun getCategories() = api.getCategories(DB_NAME)

    suspend fun getAreas(): List<String> {
        // Return cached result if available
        cachedAreas?.let { return it }

        // Fetch from API and cache
        val areas = api.getMeals(DB_NAME).mapNotNull { it.area }.distinct()
        cachedAreas = areas
        return areas
    }


    // -----------------------------
    // Local (Room) functions
    // -----------------------------
    fun getFavorites(): Flow<List<Meal>> {
        return favoriteDao.getAllFavoriteMeals()
            .map { favorites ->
                favorites.map { fav ->
                    Meal(
                        id = fav.meal.id,
                        meal = fav.meal.meal,
                        drinkAlternate = fav.meal.drinkAlternate,
                        category = fav.meal.category,
                        categoryId = fav.meal.categoryId,
                        area = fav.meal.area,
                        instructions = fav.meal.instructions,
                        mealThumb = fav.meal.mealThumb,
                        tags = fav.meal.tags,
                        youtube = fav.meal.youtube,
                        source = fav.meal.source,
                        ingredients = fav.ingredients.map { Ingredient(it.ingredient, it.measure) }
                    )
                }
            }
    }

    suspend fun addFavorite(meal: Meal) {
        val mealEntity = FavoriteMealEntity(
            id = meal.id,
            meal = meal.meal,
            drinkAlternate = meal.drinkAlternate,
            category = meal.category,
            categoryId = meal.categoryId,
            area = meal.area,
            instructions = meal.instructions,
            mealThumb = meal.mealThumb,
            tags = meal.tags,
            youtube = meal.youtube,
            source = meal.source
        )

        val ingredients = meal.ingredients.map { ing ->
            IngredientEntity(mealId = meal.id, ingredient = ing.ingredient, measure = ing.measure)
        }

        favoriteDao.insertMeal(mealEntity)
        favoriteDao.insertIngredients(ingredients)
    }

    suspend fun removeFavorite(meal: Meal) {
        val mealEntity = FavoriteMealEntity(
            id = meal.id,
            meal = meal.meal,
            drinkAlternate = meal.drinkAlternate,
            category = meal.category,
            categoryId = meal.categoryId,
            area = meal.area,
            instructions = meal.instructions,
            mealThumb = meal.mealThumb,
            tags = meal.tags,
            youtube = meal.youtube,
            source = meal.source
        )
        favoriteDao.deleteMeal(mealEntity)
    }

    suspend fun isFavorite(id: String): Boolean {
        return favoriteDao.getFavoriteMealById(id) != null
    }
}
