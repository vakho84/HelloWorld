package com.example.helloworld.domain

import kotlinx.coroutines.flow.Flow

interface ImageObjectRepository {
    fun getAll(): Flow<List<ImageObjectEntity>>
    fun getFavorites(): Flow<List<ImageObjectEntity>>
    fun getOne(id: Int?): Flow<ImageObjectEntity>

    suspend fun refreshAll()
    suspend fun refreshOne(id: Int)

    suspend fun saveOne(imageObjectEntity: ImageObjectEntity)

    fun getLocalUri(imageObjectEntity: ImageObjectEntity): String
}