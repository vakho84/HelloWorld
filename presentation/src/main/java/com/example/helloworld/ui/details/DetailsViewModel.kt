package com.example.helloworld.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.helloworld.HelloWorldApp
import com.example.helloworld.domain.ImageObjectEntity
import com.example.helloworld.domain.ImageObjectRepository

class DetailsViewModel(
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
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as HelloWorldApp
                return DetailsViewModel(
                    application.imageObjectRepository
                ) as T
            }
        }
    }

    fun load(id: Int?): LiveData<ImageObjectEntity> = imageObjectRepository.getOne(id).asLiveData()

    suspend fun refresh(id: Int) {
        imageObjectRepository.refreshOne(id)
    }
}
