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
import com.example.cooksy.ui.components.AreaChips
import com.example.cooksy.ui.components.CategoryChips
import com.example.cooksy.ui.components.MealGrid
import com.example.cooksy.ui.favorite.FavoriteViewModel

//@Composable
//fun ExploreScreen(
//    viewModel: ExploreViewModel,
//    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
//    onMealClick: (Meal) -> Unit
//) {
//    val categories by viewModel.categories.collectAsState()
//    val selectedCategory by viewModel.selectedCategory.collectAsState()
//    val areas by viewModel.areas.collectAsState()
//    val selectedArea by viewModel.selectedArea.collectAsState()
//    val meals by viewModel.meals.collectAsState()
//    val favorites by favoriteViewModel.favorites.collectAsState()
//    val isLoading by viewModel.isLoading.collectAsState()
//    val errorMessage by viewModel.errorMessage.collectAsState()
//
//    Column(modifier = Modifier.fillMaxSize()) {
//        Text(
//            text = "Explore Recipes",
//            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
//            modifier = Modifier.padding(16.dp)
//        )
//
////        CategoryChips(
////            categories = categories,
////            selectedCategory = selectedCategory,
////            onSelectCategory = { category ->
////                viewModel.selectCategory(category.category)
////            }
////        )
////
////        AreaChips(
////            areas = areas, // your list of area names from HomeScreen or ViewModel
////            selectedArea = selectedArea,
////            onSelectArea = { area ->
////                viewModel.selectArea(area)
////            }
////        )
//
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp, vertical = 8.dp),
//            horizontalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            // Category dropdown
//            var categoryExpanded by remember { mutableStateOf(false) }
//            Box {
//                TextButton(onClick = { categoryExpanded = true }) {
//                    Text(selectedCategory ?: "Category")
//                }
//                DropdownMenu(
//                    expanded = categoryExpanded,
//                    onDismissRequest = { categoryExpanded = false }
//                ) {
//                    categories.forEach { category ->
//                        DropdownMenuItem(
//                            text = { Text(category.category) },
//                            onClick = {
//                                viewModel.selectCategory(category.category)
//                                categoryExpanded = false
//                            }
//                        )
//                    }
//                }
//            }
//
//            // Area dropdown
//            var areaExpanded by remember { mutableStateOf(false) }
//            Box {
//                TextButton(onClick = { areaExpanded = true }) {
//                    Text(selectedArea ?: "Area")
//                }
//                DropdownMenu(
//                    expanded = areaExpanded,
//                    onDismissRequest = { areaExpanded = false }
//                ) {
//                    areas.forEach { area ->
//                        DropdownMenuItem(
//                            text = { Text(area) },
//                            onClick = {
//                                viewModel.selectArea(area)
//                                areaExpanded = false
//                            }
//                        )
//                    }
//                }
//            }
//        }
//
//        when {
//            isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                CircularProgressIndicator()
//            }
//
//            errorMessage != null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                Text(text = "Error: $errorMessage")
//            }
//
//            else -> MealGrid(
//                meals = meals,
//                favorites = favorites,
//                onClick = onMealClick,
//                onToggleFavorite = { favoriteViewModel.toggleFavorite(it) }
//            )
//        }
//    }
//}

@Composable
fun ExploreScreen(
    viewModel: ExploreViewModel,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onMealClick: (Meal) -> Unit
) {
    val categories by viewModel.categories.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val areas by viewModel.areas.collectAsState()
    val selectedArea by viewModel.selectedArea.collectAsState()
    val meals by viewModel.meals.collectAsState()
    val favorites by favoriteViewModel.favorites.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp) // global horizontal padding
    ) {
        Text(
            text = "Explore Recipes",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
        )

        // Filter Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Category Dropdown
            var categoryExpanded by remember { mutableStateOf(false) }
            Box(modifier = Modifier.weight(1f)) {
                OutlinedButton(onClick = { categoryExpanded = true }, modifier = Modifier.fillMaxWidth()) {
                    Text(selectedCategory ?: "Category")
                }
                DropdownMenu(expanded = categoryExpanded, onDismissRequest = { categoryExpanded = false }) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.category) },
                            onClick = {
                                viewModel.selectCategory(category.category)
                                categoryExpanded = false
                            }
                        )
                    }
                }
            }

            // Area Dropdown
            var areaExpanded by remember { mutableStateOf(false) }
            Box(modifier = Modifier.weight(1f)) {
                OutlinedButton(onClick = { areaExpanded = true }, modifier = Modifier.fillMaxWidth()) {
                    Text(selectedArea ?: "Area")
                }
                DropdownMenu(expanded = areaExpanded, onDismissRequest = { areaExpanded = false }) {
                    areas.forEach { area ->
                        DropdownMenuItem(
                            text = { Text(area) },
                            onClick = {
                                viewModel.selectArea(area)
                                areaExpanded = false
                            }
                        )
                    }
                }
            }

            // Clear Filters Button
            OutlinedButton(onClick = {
                viewModel.selectCategory(null)
                viewModel.selectArea(null)
            }) {
                Text("Clear")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        when {
            isLoading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            errorMessage != null -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error: $errorMessage",
                    color = MaterialTheme.colorScheme.error
                )
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
