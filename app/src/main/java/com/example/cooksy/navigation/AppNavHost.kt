package com.example.cooksy.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cooksy.data.local.OnboardingPreferences
import com.example.cooksy.ui.detail.MealDetailScreen
import com.example.cooksy.ui.explore.ExploreScreen
import com.example.cooksy.ui.favorite.FavoriteScreen
import com.example.cooksy.ui.home.HomeScreen
import com.example.cooksy.ui.onboarding.OnboardingScreen
import kotlinx.coroutines.launch

@Composable
fun AppNavHost(
    navController: NavHostController,
    onboardingPreferences: OnboardingPreferences
) {
    val onboardingCompleted by onboardingPreferences.onboardingCompleted.collectAsState(initial = false)
    val startDestination = if (onboardingCompleted) Screen.Home.route else Screen.OnBoarding.route

    NavHost(navController = navController, startDestination = startDestination) {

        // Onboarding
        composable(Screen.OnBoarding.route) {
            val coroutineScope = rememberCoroutineScope()

            OnboardingScreen(onFinish = {
                coroutineScope.launch {
                    onboardingPreferences.setOnboardingCompleted(true)
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.OnBoarding.route) { inclusive = true }
                    }
                }
            })
        }

        // Home
        composable(Screen.Home.route) {
            HomeScreen(
                onMealClick = { meal ->
                    navController.navigate(Screen.MealDetail.createRoute(meal.id))
                },
                onCategoryClick = { /* ignore for now */ },
                onAreaClick = { /* ignore for now */ }
            )
        }

        // Explore
        composable(Screen.Explore.route) {
            ExploreScreen(
                onMealClick = { meal ->
                    navController.navigate(Screen.MealDetail.createRoute(meal.id))
                }
            )
        }

        // Favorite
        composable(Screen.Favorite.route) {
            FavoriteScreen(onClick = { meal ->
                navController.navigate(Screen.MealDetail.createRoute(meal.id))
            })
        }

        // Meal Detail
        composable(
            route = Screen.MealDetail.route,
            arguments = listOf(navArgument("mealId") { type = NavType.StringType })
        ) { backStackEntry ->
            val mealId = backStackEntry.arguments?.getString("mealId") ?: return@composable
            MealDetailScreen(mealId = mealId)
        }
    }
}
