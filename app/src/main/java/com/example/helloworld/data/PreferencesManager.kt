package com.example.helloworld.data

import android.content.Context
import com.example.helloworld.model.AppPreferencesDataSource


class PreferencesManager {

    private var appPreferencesDataSource: AppPreferencesDataSource

      constructor (appContext: Context) {
        val sharedPreferences = appContext.getSharedPreferences("applicator", Context.MODE_PRIVATE)
        appPreferencesDataSource = AppSharedPreferencesDataSource(sharedPreferences)
    }

    val appPreferences: AppPreferencesDataSource
        get() = appPreferencesDataSource

}