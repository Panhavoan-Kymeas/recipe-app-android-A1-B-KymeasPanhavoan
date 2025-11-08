package com.example.cooksy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.cooksy.ui.explore.ExploreScreen
import com.example.cooksy.ui.home.HomeScreen
import com.example.cooksy.ui.theme.CooksyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CooksyTheme {
                // Pass a dummy lambda to HomeScreen
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
                ExploreScreen(
                    onMealClick = { meal ->
                        println("Clicked meal: ${meal.meal}")
                    }
                )
            }
        }
    }
}