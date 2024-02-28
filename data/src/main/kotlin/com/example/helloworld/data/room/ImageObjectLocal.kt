package com.example.helloworld.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.helloworld.domain.ImageObjectEntity

@Entity(tableName = "favoriteImageObjects")
internal data class ImageObjectLocal(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "author")
    var author: String,

    @ColumnInfo(name = "width")
    var width: String,

    @ColumnInfo(name = "height")
    var height: String,

    @ColumnInfo(name = "url")
    var url: String,

    @ColumnInfo(name = "download_url")
    var download_url: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean
)

internal fun ImageObjectLocal.toEntity(): ImageObjectEntity {
    return ImageObjectEntity(
        this.id,
        this.author,
        this.width,
        this.height,
        this.url,
        this.download_url,
        this.isFavorite
    )
}