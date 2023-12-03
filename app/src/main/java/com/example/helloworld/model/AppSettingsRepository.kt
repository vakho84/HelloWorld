package com.example.helloworld.model

abstract class AppSettingsRepository {

     abstract var appPreferencesDataSource: AppPreferencesDataSource

    open fun appSettingsRepository(appPreferencesDataSource: AppPreferencesDataSource?) {
        this.appPreferencesDataSource = appPreferencesDataSource!!
    }

    open fun setTheme(theme: HelloWorldTheme) {
        appPreferencesDataSource.setTheme(theme)
    }

    open fun getTheme(): HelloWorldTheme {
        return appPreferencesDataSource.getTheme()
    }

}