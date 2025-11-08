package com.example.cooksy.ui.explore

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.cooksy.data.model.Meal
import com.example.cooksy.ui.components.CategoryChips
import com.example.cooksy.ui.components.MealGrid
import com.example.cooksy.ui.favorite.FavoriteViewModel

@Composable
fun ExploreScreen(
    viewModel: ExploreViewModel = hiltViewModel(),
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onMealClick: (Meal) -> Unit
) {
    val categories by viewModel.categories.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val meals by viewModel.meals.collectAsState()
    val favorites by favoriteViewModel.favorites.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Explore Recipes",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(16.dp)
        )

        CategoryChips(
            categories = categories,
            selectedCategory = selectedCategory,
            onSelectCategory = { category ->
                viewModel.selectCategory(
                    if (selectedCategory == category.category) null else category.category
                )
            }
        )

        when {
            isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

            errorMessage != null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error: $errorMessage")
            }

            else -> MealGrid(
                meals = meals,
                favorites = favorites,
                onClick = onMealClick,
                onToggleFavorite = { favoriteViewModel.toggleFavorite(it) }
            )
        }
    }
}