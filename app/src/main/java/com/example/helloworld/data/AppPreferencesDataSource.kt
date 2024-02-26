package com.example.helloworld.data

import com.example.helloworld.model.HelloWorldLang
import com.example.helloworld.model.HelloWorldTheme

interface AppPreferencesDataSource {
    fun setTheme(helloWorldTheme: HelloWorldTheme)
    fun getTheme(): HelloWorldTheme

    fun setLang(helloWorldLang: HelloWorldLang)
    fun getLang(): HelloWorldLang
}