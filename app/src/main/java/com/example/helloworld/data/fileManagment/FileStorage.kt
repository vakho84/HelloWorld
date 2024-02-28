package com.example.helloworld.data.fileManagment

interface FileStorage {
    suspend fun download(filename: String, downloadUrl: String)
    suspend fun delete(filename: String)
    fun getUri(filename: String): String
}