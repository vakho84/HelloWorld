package com.example.helloworld.fileManagment

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class StorageMethods : Storage {

    /* override fun saveImageToDevice(imageView: ImageView, id: Int, context: Context?) {
        val fileName: String = id.toString()
        // Get the drawable from the ImageView
        val drawable = imageView.drawable

        // Create a bitmap from the drawable
        val bitmap = (drawable as BitmapDrawable).bitmap

        // Get the path to the device's gallery
        val directoryPath =
            Environment.getExternalStorageDirectory().toString() + File.separator + Environment.DIRECTORY_DOWNLOADS + File.separator + "HelloWorld"

        // Create a file to save the image
        val file = File(directoryPath, "$fileName.jpg")

        try {
            // Create an output stream to write the bitmap to the file
            val outputStream = FileOutputStream(file)

            // Compress the bitmap and write it to the output stream
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)

            // Flush and close the output stream
            outputStream.flush()
            outputStream.close()

            // Notify the media scanner about the new image

            MediaScannerConnection.scanFile(
                context,  //applicationContext
                arrayOf(file.toString()),
                null,
                null
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
*/

    override fun saveToInternalStorage(bitmapImage: Bitmap, id: Int, context: Context): String? {
        val cw = ContextWrapper(context)
        // path to /data/data/yourApp/app_data/imageDir
        val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
        // Create imageDir
        val myPath = File(directory, "$id.jpg")
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(myPath)
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return directory.absolutePath
    }

    override fun deleteFromInternalStorage(id: Int, context: Context) {
        val cw = ContextWrapper(context)
        // path to /data/data/yourApp/app_data/imageDir
        val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
        // Create imageDir
        val myPath = File(directory, "$id.jpg")
        myPath.delete()
    }

}