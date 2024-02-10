package com.example.helloworld.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteImageObjects")
data class ImageObjectEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "networkId")
    var networkId: Int,
    @ColumnInfo(name = "author")
    var author: String,
    @ColumnInfo(name = "downloadUrl")
    var downloadUrl: String
)