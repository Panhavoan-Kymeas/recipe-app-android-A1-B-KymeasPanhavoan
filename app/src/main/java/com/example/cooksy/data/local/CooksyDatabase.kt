package com.example.cooksy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cooksy.data.local.dao.FavoriteMealDao
import com.example.cooksy.data.local.entity.FavoriteMealEntity
import com.example.cooksy.data.local.entity.IngredientEntity

@Database(
    entities = [FavoriteMealEntity::class, IngredientEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CooksyDatabase : RoomDatabase() {
    abstract fun favoriteMealDao(): FavoriteMealDao
}