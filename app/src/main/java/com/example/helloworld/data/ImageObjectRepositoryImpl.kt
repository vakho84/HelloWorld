package com.example.helloworld.data

import androidx.lifecycle.asLiveData
import com.example.helloworld.data.retrofit.ImageListApi
import com.example.helloworld.data.room.ImageObjectDao
import com.example.helloworld.data.room.ImageObjectLocal
import com.example.helloworld.data.room.toEntity
import com.example.helloworld.fileManagment.Storage
import com.example.helloworld.model.ImageObjectEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class ImageObjectRepositoryImpl(
    private val imageObjectDao: ImageObjectDao,
    private val imageListApi: ImageListApi,
    private val storage: Storage
) {

    //ImageListApi
    suspend fun getOneImage(id: Int, isFavorite: Boolean): ImageObjectEntity {
        return imageListApi.getOneImage(id).toLocal(isFavorite).toEntity()
    }

    suspend fun getImageList(page: String, limit: String, isFavorite: Boolean): List<ImageObjectEntity> {
        return imageListApi.getImageList(page, limit).map { it.toLocal(isFavorite) }.map { it.toEntity() }
    }

    //ImageObjectDao
    fun insertImageObject(imageObjectWeb: ImageObjectWeb, imageObjectLocal: ImageObjectLocal) {
        imageObjectDao.insertImageObject(imageObjectWeb.toLocal(imageObjectLocal?.isFavorite ?: false))
    }

    fun loadById(id: Int): ImageObjectEntity? {
        return imageObjectDao.loadById(id)?.toEntity()
    }

    fun getById(id: Int?): Flow<ImageObjectEntity> {
        return imageObjectDao.getById(id).map { imageObjectDb -> imageObjectDb.toEntity() }
    }

}