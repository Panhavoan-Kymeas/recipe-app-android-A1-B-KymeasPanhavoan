package com.example.cooksy.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object OnBoarding : Screen("onboarding")
    object Home : Screen("home")
    object Explore : Screen("explore")
    object Favorite : Screen("favorite")
    object MealDetail : Screen("meal_detail/{mealId}") {
        fun createRoute(mealId: String) = "meal_detail/$mealId"
    }
}
