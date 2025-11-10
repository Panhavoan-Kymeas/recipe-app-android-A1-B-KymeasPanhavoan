package com.example.cooksy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.cooksy.navigation.AppNavHost
import com.example.cooksy.ui.explore.ExploreScreen
import com.example.cooksy.ui.favorite.FavoriteScreen
import com.example.cooksy.ui.home.HomeScreen
import com.example.cooksy.ui.detail.MealDetailScreen
import com.example.cooksy.ui.theme.CooksyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CooksyTheme {
//                HomeScreen(
//                    onMealClick = { meal ->
//                        println("Clicked meal: ${meal.meal}")
//                    },
//                    onCategoryClick = { category ->
//                        println("Clicked category: ${category.category}")
//                    },
//                    onAreaClick = { area ->
//                        println("Clicked area: $area")
//                    }
//                )
//                ExploreScreen(
//                    onMealClick = { meal ->
//                        println("Clicked meal: ${meal.meal}")
//                    }
//                )
//                FavoriteScreen(
//                    onClick = {
//                        meal -> println("Click meal: ${meal.meal}")
//                    }
//                )
//                MealDetailScreen(
//                    "52883"
//                )
                AppNavHost()
            }
        }
    }
}