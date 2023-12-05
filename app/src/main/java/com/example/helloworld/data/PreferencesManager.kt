package com.example.helloworld.data

import android.content.Context
import com.example.helloworld.model.AppPreferencesDataSource


class PreferencesManager(appContext: Context) {

    private var appPreferencesDataSource: AppPreferencesDataSource

    init {
        val sharedPreferences = appContext.getSharedPreferences("helloWorld", Context.MODE_PRIVATE)
        appPreferencesDataSource = AppSharedPreferencesDataSource(sharedPreferences)
    }

    fun getAppPreferences(): AppPreferencesDataSource {
        return appPreferencesDataSource
    }
}