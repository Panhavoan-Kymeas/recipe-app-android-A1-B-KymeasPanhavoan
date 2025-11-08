package com.example.cooksy.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.cooksy.data.model.Category
import com.example.cooksy.data.model.Meal
import com.example.cooksy.ui.components.AreaSection
import com.example.cooksy.ui.components.CategorySection
import com.example.cooksy.ui.components.ChefSuggestionSection
import com.example.cooksy.ui.components.PopularMealsSection
import com.example.cooksy.ui.favorite.FavoriteViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onMealClick: (Meal) -> Unit,
    onCategoryClick: (Category) -> Unit,
    onAreaClick: (String) -> Unit
) {
    val popularMeals by homeViewModel.popularMeals.collectAsState()
    val randomMeal by homeViewModel.randomMeal.collectAsState()
    val categories by homeViewModel.categories.collectAsState()
    val areas by homeViewModel.areas.collectAsState()
    val favorites by favoriteViewModel.favorites.collectAsState()
    val isLoading by homeViewModel.isLoading.collectAsState()
    val error by homeViewModel.errorMessage.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (error != null) {
            Text(text = error ?: "Unknown error", modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn {
                item { ChefSuggestionSection(randomMeal, onClick = onMealClick) }
                item {
                    PopularMealsSection(
                        meals = popularMeals,
                        favorites = favorites,
                        onClick = onMealClick,
                        onToggleFavorite = { favoriteViewModel.toggleFavorite(it) }
                    )
                }
                item { CategorySection(categories, onClick = onCategoryClick) }
                item { AreaSection(areas, onClick = onAreaClick) }
            }
        }
    }
}
