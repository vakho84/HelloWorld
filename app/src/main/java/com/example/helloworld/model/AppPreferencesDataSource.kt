package com.example.helloworld.model

interface AppPreferencesDataSource {
    fun setTheme(helloWorldTheme: HelloWorldTheme)
    fun getTheme(): HelloWorldTheme
}