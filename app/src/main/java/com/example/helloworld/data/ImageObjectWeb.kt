package com.example.helloworld.data

import com.example.helloworld.data.room.ImageObjectLocal

data class ImageObjectWeb(
    val id: Int,
    val author: String,
    val width: String,
    val height: String,
    val url: String,
    val download_url: String
)

fun ImageObjectWeb.toLocal(isFavorite: Boolean): ImageObjectLocal {
    return ImageObjectLocal(
        this.id,
        this.author,
        this.width,
        this.height,
        this.url,
        this.download_url,
        isFavorite
    )
}