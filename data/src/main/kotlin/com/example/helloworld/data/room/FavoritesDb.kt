package com.example.helloworld.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ImageObjectLocal::class], version = 1)
internal abstract class FavoritesDb : RoomDatabase() {
    abstract fun getDao(): ImageObjectDao
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