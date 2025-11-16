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
import com.example.cooksy.SplashScreen
import com.example.cooksy.data.local.OnboardingPreferences
import com.example.cooksy.ui.detail.MealDetailScreen
import com.example.cooksy.ui.explore.ExploreScreen
import com.example.cooksy.ui.explore.ExploreViewModel
import com.example.cooksy.ui.favorite.FavoriteScreen
import com.example.cooksy.ui.home.HomeScreen
import com.example.cooksy.ui.onboarding.OnBoardingScreen
import kotlinx.coroutines.launch

@Composable
fun AppNavHost(
    navController: NavHostController,
    onboardingPreferences: OnboardingPreferences,
    exploreViewModel: ExploreViewModel
) {
    val onboardingCompleted by onboardingPreferences.onboardingCompleted.collectAsState(initial = false)
    val startDestination = Screen.Splash.route

    NavHost(navController = navController, startDestination = startDestination) {

        // Splash
        composable(Screen.Splash.route) {
            SplashScreen(onTimeout = {
                if (onboardingCompleted) {
                    navController.navigate("home") {
                        popUpTo("splash") { inclusive = true }
                    }
                } else {
                    navController.navigate("onboarding") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            })
        }


        // Onboarding
        composable(Screen.OnBoarding.route) {
            val coroutineScope = rememberCoroutineScope()

            OnBoardingScreen(onFinish = {
                coroutineScope.launch {
                    onboardingPreferences.setOnboardingCompleted(true)
                    navController.navigate("home") {
                        popUpTo("onboarding") { inclusive = true }
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
                onCategoryClick = { category ->
                    // Select category in ExploreViewModel
                    exploreViewModel.selectCategory(category.category)
                    // Navigate to Explore tab
                    navController.navigate(Screen.Explore.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(Screen.Home.route) { saveState = true }
                    }
                },
                onAreaClick = { area ->
                    // Select area in ExploreViewModel
                    exploreViewModel.selectArea(area)
                    // Navigate to Explore tab
                    navController.navigate(Screen.Explore.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(Screen.Home.route) { saveState = true }
                    }
                }
            )
        }

        // Explore
        composable(Screen.Explore.route) {
            ExploreScreen(
                viewModel = exploreViewModel,
                onMealClick = { meal ->
                    navController.navigate(Screen.MealDetail.createRoute(meal.id))
                }
            )
        }

        // Favorite
        composable(Screen.Favorite.route) {
            FavoriteScreen(onClick = { meal ->
                navController.navigate(Screen.MealDetail.createRoute(meal.id)) })
        }

        composable(
            route = Screen.MealDetail.route,
            arguments = listOf(navArgument("mealId") { type = NavType.StringType })
        ) { backStackEntry ->
            val mealId = backStackEntry.arguments?.getString("mealId") ?: return@composable
            MealDetailScreen(mealId = mealId)
        }
    }
}