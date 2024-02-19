package com.example.helloworld.fileManagment

interface Storage {
    suspend fun saveToInternalStorage(id: Int, downloadUrl: String)
    suspend fun deleteFromInternalStorage(id: Int)
    fun getUrl(id: Int, remoteUrl: String): String
}