package com.example.helloworld.data.fileManagment

import android.content.Context
import android.util.Log
import androidx.core.net.toUri
import java.io.File
import java.net.URL

const val TAG = "StorageMethods"

class LocalFileStorage(
    val context: Context,
    private val imageDir: File = context.getDir("imageDir", Context.MODE_PRIVATE)
) : FileStorage {
    init {
        imageDir.mkdirs()
    }

    override suspend fun download(filename: String, downloadUrl: String) {
        try {
            File(imageDir, filename).outputStream().use { outputStream ->
                val buffer = ByteArray(16 * 1024)
                URL(downloadUrl).openStream()?.buffered().use { inputStream ->
                    if (inputStream != null) {
                        while (true) {
                            val num = inputStream.read(buffer)
                            if (num <= 0) {
                                break
                            }
                            outputStream.write(buffer, 0, num)
                        }
                    }
                }
            }
        } catch (t: Throwable) {
            Log.e(TAG, "Error saving file", t)
        }
    }

    override suspend fun delete(filename: String) {
        File(imageDir, filename).delete()
    }

    override fun getUri(filename: String): String {
        val file = File(imageDir, filename)
        return file.toUri().toString()
    }
}