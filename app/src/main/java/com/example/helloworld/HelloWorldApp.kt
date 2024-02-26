package com.example.helloworld

import android.app.Application
import android.content.res.Resources
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import com.example.helloworld.data.AppSharedPreferencesDataSource
import com.example.helloworld.data.room.FavoritesDb
import com.example.helloworld.data.room.ImageObjectDao
import com.example.helloworld.fileManagment.Storage
import com.example.helloworld.fileManagment.StorageMethods
import com.example.helloworld.data.AppSettingsRepository
import com.example.helloworld.model.HelloWorldTheme
import com.example.helloworld.data.retrofit.ImageListApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale

class HelloWorldApp : Application() {
    private lateinit var _storage: Storage
    private lateinit var _imageListApi: ImageListApi
    private lateinit var _imageObjectDao: ImageObjectDao

    private lateinit var appSettingsRepository: AppSettingsRepository

    val storage get() = _storage
    val imageListApi get() = _imageListApi
    val imageObjectDao get() = _imageObjectDao

    companion object {
        fun changeTheme(theme: HelloWorldTheme?) {
            val mode: Int = when (theme) {
                HelloWorldTheme.Dark -> AppCompatDelegate.MODE_NIGHT_YES
                HelloWorldTheme.Light -> AppCompatDelegate.MODE_NIGHT_NO
                HelloWorldTheme.System -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                else -> throw IllegalArgumentException()
            }
            AppCompatDelegate.setDefaultNightMode(mode)
        }

        fun isSystemLanguageRu(): Boolean {
            val systemConfig = Resources.getSystem().configuration
            val systemLocales = systemConfig.locales
            val localeIndexClosestToRu = findLanguage(systemLocales, "ru")
            val localeIndexClosestToEn = findLanguage(systemLocales, "en")
            return if (localeIndexClosestToRu == -1) {
                false
            } else {
                if (localeIndexClosestToEn == -1) {
                    true
                } else {
                    localeIndexClosestToEn >= localeIndexClosestToRu
                }
            }
        }

        private fun findLanguage(systemLocales: LocaleList, language: String): Int {
            for (i in 0 until systemLocales.size()) {
                val locale = systemLocales[0]
                if (locale.language == Locale(language).language) {
                    return i
                }
            }
            return -1
        }
    }

    override fun onCreate() {
        super.onCreate()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://picsum.photos")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        _imageListApi = retrofit.create(ImageListApi::class.java)

        _imageObjectDao = FavoritesDb.getDb(this).getDao()

        _storage = StorageMethods(this)

        val appPreferences = AppSharedPreferencesDataSource(this)
        appSettingsRepository = AppSettingsRepository(appPreferences)
        changeTheme(appPreferences.getTheme())
    }

    fun getAppSettingsRepository(): AppSettingsRepository {
        return appSettingsRepository
    }
}
