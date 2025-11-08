package com.example.cooksy.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "meal_ingredients",
    foreignKeys = [
        ForeignKey(
            entity = FavoriteMealEntity::class,
            parentColumns = ["id"],
            childColumns = ["mealId"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ],
    indices = [Index("mealId")]
)
data class IngredientEntity(
    @PrimaryKey(autoGenerate = true) val ingredientId: Int = 0,
    val mealId: String,
    val ingredient: String,
    val measure: String
)