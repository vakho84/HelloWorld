package com.example.helloworld.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ImageObjectEntity::class], version = 1)
abstract class FavoritesDb : RoomDatabase() {

    companion object {
        fun getDb(context: Context): FavoritesDb {
            return Room.databaseBuilder(
                context.applicationContext,
                FavoritesDb::class.java,
                "favorites.db"
            ).build()
        }
    }
}