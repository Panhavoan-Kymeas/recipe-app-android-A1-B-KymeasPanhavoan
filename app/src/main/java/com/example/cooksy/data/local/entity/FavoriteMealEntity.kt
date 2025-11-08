package com.example.cooksy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_meals")
data class FavoriteMealEntity(
    @PrimaryKey val id: String,
    val meal: String,
    val drinkAlternate: String?,
    val category: String,
    val categoryId: String,
    val area: String,
    val instructions: String,
    val mealThumb: String,
    val tags: String?,
    val youtube: String?,
    val source: String?
)