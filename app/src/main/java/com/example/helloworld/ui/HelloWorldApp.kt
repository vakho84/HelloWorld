package com.example.helloworld.ui

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.helloworld.data.AppSharedPreferencesDataSource
import com.example.helloworld.model.AppSettingsRepository
import com.example.helloworld.model.HelloWorldTheme

class HelloWorldApp : Application() {

    private lateinit var appSettingsRepository: AppSettingsRepository

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
    }

    override fun onCreate() {
        super.onCreate()

        val appPreferences = AppSharedPreferencesDataSource(this)

        appSettingsRepository = AppSettingsRepository(appPreferences)

        changeTheme(appPreferences.getTheme())
    }

    fun getAppSettingsRepository(): AppSettingsRepository {
        return appSettingsRepository
    }
}