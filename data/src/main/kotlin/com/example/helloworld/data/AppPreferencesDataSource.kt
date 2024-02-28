package com.example.helloworld.data

import com.example.helloworld.domain.HelloWorldLang
import com.example.helloworld.domain.HelloWorldTheme

internal interface AppPreferencesDataSource {
    fun setTheme(helloWorldTheme: HelloWorldTheme)
    fun getTheme(): HelloWorldTheme

    fun setLang(helloWorldLang: HelloWorldLang)
    fun getLang(): HelloWorldLang
}