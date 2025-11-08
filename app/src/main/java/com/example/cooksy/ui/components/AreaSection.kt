package com.example.cooksy.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items

@Composable
fun AreaSection(
    areas: List<String>,
    onClick: (String) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text("Areas", style = MaterialTheme.typography.titleMedium)
        LazyRow {
            items(areas, key = { it }) { area ->
                ChipCard(
                    text = area,
                    modifier = Modifier.padding(end = 8.dp)
                ) { onClick(area) }
            }
        }
    }
}