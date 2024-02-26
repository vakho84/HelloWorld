package com.example.helloworld.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.helloworld.HelloWorldApp
import com.example.helloworld.data.room.ImageObjectDao
import com.example.helloworld.data.room.ImageObjectLocal
import com.example.helloworld.data.room.toEntity
import com.example.helloworld.fileManagment.Storage
import com.example.helloworld.model.ImageObjectEntity
import kotlinx.coroutines.flow.map

class FavoritesViewModel(
    private val imageObjectDao: ImageObjectDao,
    private val storage: Storage
) : ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY]) as HelloWorldApp
                return FavoritesViewModel(
                    application.imageObjectDao,
                    application.storage
                ) as T
            }
        }
    }

    fun getList(): LiveData<List<ImageObjectEntity>> = imageObjectDao.getFavorites().map {
        it.map { imageObjectDb -> imageObjectDb.toEntity() }
    }.asLiveData()

    suspend fun update(imageObjectEntity: ImageObjectEntity) {
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
        if (imageObjectEntity.isFavorite) {
            storage.saveToInternalStorage(imageObjectEntity.id, imageObjectEntity.downloadUrl)
        } else {
            storage.deleteFromInternalStorage(imageObjectEntity.id)
        }
    }
}