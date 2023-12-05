package com.example.helloworld.model

 open class AppSettingsRepository {

     private var appPreferencesDataSource: AppPreferencesDataSource? = null

    open fun appSettingsRepository(appPreferencesDataSource: AppPreferencesDataSource?) {
        this.appPreferencesDataSource = appPreferencesDataSource!!
    }

    open fun setTheme(theme: HelloWorldTheme) {
        appPreferencesDataSource?.setTheme(theme)
    }

    open fun getTheme(): HelloWorldTheme {
        return appPreferencesDataSource!!.getTheme()
    }

}