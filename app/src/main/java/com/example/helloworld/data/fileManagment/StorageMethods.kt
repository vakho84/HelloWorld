package com.example.helloworld.data.fileManagment

import android.content.Context
import android.util.Log
import androidx.core.net.toUri
import java.io.File
import java.net.URL

const val TAG = "StorageMethods"

class StorageMethods(
    val context: Context,
    private val imageDir: File = context.getDir("imageDir", Context.MODE_PRIVATE)
) : Storage {
    init {
        imageDir.mkdirs()
    }

    override suspend fun saveToInternalStorage(id: Int, downloadUrl: String) {
        try {
            File(imageDir, id.toString()).outputStream().use { outputStream ->
                val buffer = ByteArray(16 * 1024)
                URL(downloadUrl).openStream()?.buffered().use { inputStream ->
                    if (inputStream != null) {
                        while (true) {
                            val num = inputStream.read(buffer)
                            if (num <= 0) {
                                break;
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

    override suspend fun deleteFromInternalStorage(id: Int) {
        File(imageDir, id.toString()).delete()
    }

    override fun getUrl(id: Int, remoteUrl: String): String {
        val file = File(imageDir, id.toString())
        return if (file.exists()) {
            file.toUri().toString()
        } else {
            remoteUrl
        }
    }
}