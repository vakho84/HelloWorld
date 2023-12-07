package com.example.helloworld.model

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