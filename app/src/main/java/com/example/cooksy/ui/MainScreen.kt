package com.example.cooksy.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cooksy.data.local.OnboardingPreferences
import com.example.cooksy.navigation.AppNavHost
import com.example.cooksy.ui.components.BottomNavBar
import androidx.compose.ui.Modifier
import com.example.cooksy.navigation.Screen

@Composable
fun MainScreen(onboardingPreferences: OnboardingPreferences) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.OnBoarding.route) {
                BottomNavBar(navController = navController)
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            AppNavHost(
                navController = navController,
                onboardingPreferences = onboardingPreferences
            )
        }
    }
}
