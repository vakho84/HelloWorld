package com.example.helloworld.fileManagment

import android.content.Context
import android.graphics.Bitmap

interface Storage {
    //fun saveImageToDevice(imageView: ImageView, id: Int, context: Context?)
    fun saveToInternalStorage(bitmapImage: Bitmap, id: Int, context: Context): String?
    fun deleteFromInternalStorage(id: Int, context: Context)

}