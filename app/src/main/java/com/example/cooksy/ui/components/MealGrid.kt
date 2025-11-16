package com.example.cooksy.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cooksy.data.model.Meal
import androidx.compose.foundation.lazy.grid.items

@Composable
fun MealGrid(
    meals: List<Meal>,
    favorites: List<Meal>,
    onClick: (Meal) -> Unit,
    onToggleFavorite: (Meal) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(meals) { meal ->
            val isFav = favorites.any { it.id == meal.id }
            MealCard(
                meal = meal,
                modifier = Modifier.padding(4.dp),
                onClick = { onClick(meal) },
                isFavorite = isFav,
                onToggleFavorite = { onToggleFavorite(meal) }
            )
        }
    }
}
