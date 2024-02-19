package com.example.helloworld.model

data class ImageObjectEntity(
    val id: Int,
    val author: String,
    val width: String,
    val height: String,
    val url: String,
    val downloadUrl: String,
    val isFavorite: Boolean
)