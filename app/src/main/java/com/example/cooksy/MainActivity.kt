package com.example.cooksy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.cooksy.data.local.OnboardingPreferences
import com.example.cooksy.navigation.AppNavHost
import com.example.cooksy.ui.MainScreen
import com.example.cooksy.ui.explore.ExploreScreen
import com.example.cooksy.ui.favorite.FavoriteScreen
import com.example.cooksy.ui.home.HomeScreen
import com.example.cooksy.ui.detail.MealDetailScreen
import com.example.cooksy.ui.theme.CooksyTheme
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var onboardingPreferences: OnboardingPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CooksyTheme {
                MainScreen(onboardingPreferences = onboardingPreferences)
            }
        }
    }
}