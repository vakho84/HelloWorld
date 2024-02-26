package com.example.helloworld.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.helloworld.HelloWorldApp
import com.example.helloworld.data.room.ImageObjectDao
import com.example.helloworld.data.room.toEntity
import com.example.helloworld.data.toLocal
import com.example.helloworld.model.ImageObjectEntity
import com.example.helloworld.data.retrofit.ImageListApi
import com.example.helloworld.ui.home.TAG
import kotlinx.coroutines.flow.map

class DetailsViewModel(
    private val imageListApi: ImageListApi,
    private val imageObjectDao: ImageObjectDao,
) : ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as HelloWorldApp
                return DetailsViewModel(
                    application.imageListApi,
                    application.imageObjectDao,
                ) as T
            }
        }
    }

    fun load(id: Int?): LiveData<ImageObjectEntity> = imageObjectDao.getById(id).map { imageObjectDb ->
        imageObjectDb.toEntity()
    }.asLiveData()

    suspend fun refresh(id: Int) {
        try {
            val imageObjectWeb = imageListApi.getOneImage(id)
            val imageObjectLocal = imageObjectDao.loadById(imageObjectWeb.id)
            imageObjectDao.insertImageObject(
                imageObjectWeb.toLocal(imageObjectLocal?.isFavorite ?: false)
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error loading images from the web", e)
        }
    }
}
