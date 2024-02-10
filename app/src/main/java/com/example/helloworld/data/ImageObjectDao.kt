package com.example.helloworld.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface ImageObjectDao {
    @Insert
    fun insertImageObject (imageObject: ImageObject)

    @Delete
    fun deleteImageObject (imageObject: ImageObject)

    @Query("SELECT * FROM favoriteImageObjects")
    fun getAllImageObjects(): Flow<List<ImageObject>>
}