package com.example.helloworld.data

import android.util.Log
import com.example.helloworld.data.fileManagment.FileStorage
import com.example.helloworld.data.retrofit.ImageListApi
import com.example.helloworld.data.room.ImageObjectDao
import com.example.helloworld.data.room.ImageObjectLocal
import com.example.helloworld.data.room.toEntity
import com.example.helloworld.domain.ImageObjectEntity
import com.example.helloworld.domain.ImageObjectRepository
import com.example.helloworld.domain.getFilename
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val TAG = "ImageObjectRepository"

internal class ImageObjectRepositoryImpl(
    private val imageObjectDao: ImageObjectDao,
    private val imageListApi: ImageListApi,
    private val fileStorage: FileStorage
) : ImageObjectRepository {
    override fun getAll(): Flow<List<ImageObjectEntity>> {
        return imageObjectDao.getAllImageObjects().map {
            it.map { imageObjectDb -> imageObjectDb.toEntity() }
        }
    }

    override fun getFavorites(): Flow<List<ImageObjectEntity>> {
        return imageObjectDao.getFavorites().map {
            it.map { imageObjectDb -> imageObjectDb.toEntity() }
        }
    }

    override fun getOne(id: Int?): Flow<ImageObjectEntity> {
        return imageObjectDao.getById(id).map { imageObjectDb -> imageObjectDb.toEntity() }
    }

    override suspend fun refreshAll() {
        try {
            val imageList = imageListApi.getImageList(30, 5)
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

    override suspend fun refreshOne(id: Int) {
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

    override suspend fun saveOne(imageObjectEntity: ImageObjectEntity) {
        if (imageObjectEntity.isFavorite) {
            fileStorage.download(imageObjectEntity.getFilename(), imageObjectEntity.downloadUrl)
        } else {
            fileStorage.delete(imageObjectEntity.getFilename())
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

    override fun getLocalUri(imageObjectEntity: ImageObjectEntity): String {
        return fileStorage.getUri(imageObjectEntity.getFilename())
    }
}
