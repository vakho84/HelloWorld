package com.example.helloworld.data

import android.util.Log
import com.example.helloworld.data.fileManagment.Storage
import com.example.helloworld.data.retrofit.ImageListApi
import com.example.helloworld.data.room.ImageObjectDao
import com.example.helloworld.data.room.ImageObjectLocal
import com.example.helloworld.data.room.toEntity
import com.example.helloworld.model.ImageObjectEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val TAG = "ImageObjectRepository"

class ImageObjectRepository(
    private val imageObjectDao: ImageObjectDao,
    private val imageListApi: ImageListApi,
    private val storage: Storage
) {
    fun getAll(): Flow<List<ImageObjectEntity>> {
        return imageObjectDao.getAllImageObjects().map {
            it.map { imageObjectDb -> imageObjectDb.toEntity() }
        }
    }

    fun getFavorites(): Flow<List<ImageObjectEntity>> {
        return imageObjectDao.getFavorites().map {
            it.map { imageObjectDb -> imageObjectDb.toEntity() }
        }
    }

    fun getOne(id: Int?): Flow<ImageObjectEntity> {
        return imageObjectDao.getById(id).map { imageObjectDb -> imageObjectDb.toEntity() }
    }

    suspend fun refreshAll() {
        try {
            val imageList = imageListApi.getImageList(5, 30)
            imageList.forEach { imageObjectWeb ->
                val imageObjectLocal = imageObjectDao.loadById(imageObjectWeb.id)
                imageObjectDao.insertImageObject(
                    imageObjectWeb.toLocal(imageObjectLocal?.isFavorite ?: false)
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error loading images", e)
        }
    }

    suspend fun refreshOne(id: Int) {
        try {
            val imageObjectWeb = imageListApi.getOneImage(id)
            val imageObjectLocal = imageObjectDao.loadById(imageObjectWeb.id)
            imageObjectDao.insertImageObject(
                imageObjectWeb.toLocal(imageObjectLocal?.isFavorite ?: false)
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error loading images", e)
        }
    }

    suspend fun saveOne(imageObjectEntity: ImageObjectEntity) {
        if (imageObjectEntity.isFavorite) {
            storage.saveToInternalStorage(imageObjectEntity.id, imageObjectEntity.downloadUrl)
        } else {
            storage.deleteFromInternalStorage(imageObjectEntity.id)
        }
        imageObjectDao.insertImageObject(
            ImageObjectLocal(
                imageObjectEntity.id,
                imageObjectEntity.author,
                imageObjectEntity.width,
                imageObjectEntity.height,
                imageObjectEntity.url,
                imageObjectEntity.downloadUrl,
                imageObjectEntity.isFavorite
            )
        )
    }

    fun getUrl(id: Int, downloadUrl: String): String {
        return storage.getUrl(id, downloadUrl)
    }
}
