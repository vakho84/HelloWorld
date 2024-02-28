package com.example.helloworld.data

import android.content.Context
import com.example.helloworld.data.fileManagment.LocalFileStorage
import com.example.helloworld.data.retrofit.ImageListApi
import com.example.helloworld.data.room.FavoritesDb
import com.example.helloworld.domain.AppSettingsRepository
import com.example.helloworld.domain.ImageObjectRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun createImageObjectRepository(context: Context): ImageObjectRepository {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://picsum.photos")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val imageListApi = retrofit.create(ImageListApi::class.java)

    val imageObjectDao = FavoritesDb.getDb(context).getDao()

    val fileStorage = LocalFileStorage(context)

    return ImageObjectRepositoryImpl(imageObjectDao, imageListApi, fileStorage)
}

fun createAppSettingsRepository(context: Context): AppSettingsRepository {
    val appPreferences = AppSharedPreferencesDataSource(context)
    return AppSettingsRepositoryImpl(appPreferences)
}