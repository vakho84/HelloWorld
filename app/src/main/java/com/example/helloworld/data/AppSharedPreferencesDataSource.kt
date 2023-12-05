package com.example.helloworld.data

import android.content.SharedPreferences
import com.example.helloworld.model.AppPreferencesDataSource
import com.example.helloworld.model.HelloWorldTheme

const val KEY_THEME = "KEY_THEME"
private const val THEME_DARK = 0
private const val THEME_LIGHT = 1
private const val THEME_SYSTEM = 2

class AppSharedPreferencesDataSource(private var sharedPreferences: SharedPreferences) : AppPreferencesDataSource {

    override fun setTheme(helloWorldTheme: HelloWorldTheme) {
        val intValue: Int  = when (helloWorldTheme) {
            HelloWorldTheme.Dark -> THEME_DARK
            HelloWorldTheme.Light -> THEME_LIGHT
            HelloWorldTheme.System -> THEME_SYSTEM

        }
        sharedPreferences.edit().putInt(KEY_THEME, intValue).apply()
    }

    override fun getTheme(): HelloWorldTheme {
        val helloWorldTheme: HelloWorldTheme
        val intValue: Int = sharedPreferences.getInt(KEY_THEME, THEME_SYSTEM)
        helloWorldTheme = when (intValue) {
            THEME_DARK -> HelloWorldTheme.Dark
            THEME_LIGHT -> HelloWorldTheme.Light
            THEME_SYSTEM -> HelloWorldTheme.System
            else -> throw IllegalStateException()
        }
        return helloWorldTheme
    }

}