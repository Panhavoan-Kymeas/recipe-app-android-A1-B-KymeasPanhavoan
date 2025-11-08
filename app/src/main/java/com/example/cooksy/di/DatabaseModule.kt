package com.example.cooksy.di

import android.content.Context
import androidx.room.Room
import com.example.cooksy.data.local.CooksyDatabase
import com.example.cooksy.data.local.dao.FavoriteMealDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CooksyDatabase {
        return Room.databaseBuilder(
            context,
            CooksyDatabase::class.java,
            "cooksy_db"
        ).build()
    }

    @Provides
    fun provideFavoriteMealDao(db: CooksyDatabase): FavoriteMealDao {
        return db.favoriteMealDao()
    }
}
