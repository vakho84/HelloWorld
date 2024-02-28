package com.example.helloworld.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ImageObjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImageObject (imageObject: ImageObjectLocal)

    @Delete
    fun deleteImageObject (imageObject: ImageObjectLocal)

    @Query("SELECT * FROM favoriteImageObjects")
    fun getAllImageObjects(): Flow<List<ImageObjectLocal>>

    @Query("SELECT * FROM favoriteImageObjects WHERE id=:id")
    fun loadById(id: Int): ImageObjectLocal?

    @Query("SELECT * FROM favoriteImageObjects WHERE id=:id")
    fun getById(id: Int?): Flow<ImageObjectLocal>

    @Query("SELECT * FROM favoriteImageObjects WHERE isFavorite=1")
    fun getFavorites(): Flow<List<ImageObjectLocal>>
}