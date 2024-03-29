package com.example.helloworld.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.helloworld.HelloWorldApp
import com.example.helloworld.domain.ImageObjectEntity
import com.example.helloworld.domain.ImageObjectRepository

class HomeViewModel(
    private val imageObjectRepository: ImageObjectRepository
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
                return HomeViewModel(
                    application.imageObjectRepository
                ) as T
            }
        }
    }

    fun getList(): LiveData<List<ImageObjectEntity>> = imageObjectRepository.getAll().asLiveData()

    suspend fun refresh() {
        imageObjectRepository.refreshAll()
    }

    suspend fun update(imageObjectEntity: ImageObjectEntity) {
        imageObjectRepository.saveOne(imageObjectEntity)
    }
}