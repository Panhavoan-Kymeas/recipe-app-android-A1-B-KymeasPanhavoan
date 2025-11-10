package com.example.cooksy.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Home : BottomNavItem("home", Icons.Filled.Home, "Home")
    object Explore : BottomNavItem("explore", Icons.Filled.Search, "Explore")
    object Favorites : BottomNavItem("favorites", Icons.Filled.Favorite, "Favorites")
}
