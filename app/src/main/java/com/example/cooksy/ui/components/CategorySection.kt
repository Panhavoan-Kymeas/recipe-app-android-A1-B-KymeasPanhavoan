package com.example.cooksy.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cooksy.data.model.Category
import androidx.compose.foundation.lazy.items

@Composable
fun CategorySection(
    categories: List<Category>,
    onClick: (Category) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            text = "Categories",
            style = MaterialTheme.typography.titleMedium
        )

        LazyRow(contentPadding = PaddingValues(vertical = 8.dp)) {
            items(categories, key = { it.id }) { category ->
                CategoryCard(category = category, onClick = onClick)
            }
        }
    }
}