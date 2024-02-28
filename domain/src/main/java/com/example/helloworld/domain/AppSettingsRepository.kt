package com.example.helloworld.domain

interface AppSettingsRepository {
    fun setTheme(theme: HelloWorldTheme)
    fun getTheme(): HelloWorldTheme
    fun setLang(lang: HelloWorldLang)
    fun getLang(): HelloWorldLang
}