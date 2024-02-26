package com.example.helloworld.data

import com.example.helloworld.model.HelloWorldLang
import com.example.helloworld.model.HelloWorldTheme

class AppSettingsRepository(private val appPreferencesDataSource: AppPreferencesDataSource) {

    fun setTheme(theme: HelloWorldTheme) {
        appPreferencesDataSource.setTheme(theme)
    }

    fun getTheme(): HelloWorldTheme {
        return appPreferencesDataSource.getTheme()
    }

    fun setLang(lang: HelloWorldLang){
        appPreferencesDataSource.setLang(lang)
    }
    fun getLang(): HelloWorldLang {
        return appPreferencesDataSource.getLang()
    }

}