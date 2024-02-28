package com.example.helloworld.domain

data class ImageObjectEntity(
    val id: Int,
    val author: String,
    val width: String,
    val height: String,
    val url: String,
    val downloadUrl: String,
    val isFavorite: Boolean
)

fun ImageObjectEntity.getFilename(): String = this.id.toString()