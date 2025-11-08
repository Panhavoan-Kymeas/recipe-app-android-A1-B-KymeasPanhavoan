package com.example.cooksy.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cooksy.data.model.Meal
import androidx.compose.foundation.lazy.items

@Composable
fun PopularMealsSection(
    meals: List<Meal>,
    favorites: List<Meal>,
    onClick: (Meal) -> Unit,
    onToggleFavorite: (Meal) -> Unit
) {
    Column(modifier = Modifier.padding(start = 16.dp, top = 8.dp)) {
        Text("Popular Meals", style = MaterialTheme.typography.titleMedium)
        LazyRow {
            items(meals) { meal ->
                val isFav = favorites.any { it.id == meal.id }
                MealCard(
                    meal = meal,
                    modifier = Modifier.padding(8.dp),
                    onClick = { onClick(meal) },
                    isFavorite = isFav,
                    onToggleFavorite = { onToggleFavorite(meal) }
                )
            }
        }
    }
}