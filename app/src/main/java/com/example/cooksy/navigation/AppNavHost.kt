package com.example.cooksy.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.cooksy.data.local.OnboardingPreferences
import com.example.cooksy.ui.components.BottomNavigationBar
import com.example.cooksy.ui.detail.MealDetailScreen
import com.example.cooksy.ui.explore.ExploreScreen
import com.example.cooksy.ui.favorite.FavoriteScreen
import com.example.cooksy.ui.home.HomeScreen
import com.example.cooksy.ui.onboarding.OnboardingScreen
import kotlinx.coroutines.launch

@Composable
fun AppNavHost(startDestination: String = "onboarding") {
    val context = LocalContext.current
    val prefs = remember { OnboardingPreferences(context) }
    val onboardingCompleted by prefs.onboardingCompleted.collectAsState(initial = false)

    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    val startDestination = if (onboardingCompleted) "home" else "onboarding"

    // Scaffold to show bottom bar on main tabs
    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            if (currentRoute in listOf("home", "explore", "favorites")) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues)
        ) {
            // Onboarding
            composable("onboarding") {
                OnboardingScreen(onFinish = {
                    scope.launch {
                        prefs.setOnboardingCompleted(true)
                    }
                    navController.navigate("home") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                })
            }

            // Home
            composable("home") {
                HomeScreen(
                    onMealClick = { meal ->
                        navController.navigate("mealDetail/${meal.id}")
                    },
                    onCategoryClick = { category -> /* implement */ }, // TODO: ADD THIS
                    onAreaClick = { area -> /* implement */ } // TODO: ADD THIS
                )
            }

            // Explore
            composable("explore") {
                ExploreScreen(
                    onMealClick = { meal ->
                        navController.navigate("mealDetail/${meal.id}")
                    }
                )
            }

            // Favorites
            composable("favorites") {
                FavoriteScreen(
                    onClick = { meal ->
                        navController.navigate("mealDetail/${meal.id}")
                    }
                )
            }

            // Meal Detail
            composable(
                "mealDetail/{mealId}",
                arguments = listOf(navArgument("mealId") { type = NavType.StringType })
            ) { backStackEntry ->
                val mealId = backStackEntry.arguments?.getString("mealId") ?: ""
                MealDetailScreen(mealId = mealId)
            }
        }
    }
}