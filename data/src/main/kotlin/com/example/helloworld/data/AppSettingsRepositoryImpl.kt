package com.example.helloworld.data

import com.example.helloworld.domain.AppSettingsRepository
import com.example.helloworld.domain.HelloWorldLang
import com.example.helloworld.domain.HelloWorldTheme

internal class AppSettingsRepositoryImpl(
    private val appPreferencesDataSource: AppPreferencesDataSource
) : AppSettingsRepository {
    override fun setTheme(theme: HelloWorldTheme) {
        appPreferencesDataSource.setTheme(theme)
    }

    override fun getTheme(): HelloWorldTheme {
        return appPreferencesDataSource.getTheme()
    }

    override fun setLang(lang: HelloWorldLang){
        appPreferencesDataSource.setLang(lang)
    }
    override fun getLang(): HelloWorldLang {
        return appPreferencesDataSource.getLang()
    }
}