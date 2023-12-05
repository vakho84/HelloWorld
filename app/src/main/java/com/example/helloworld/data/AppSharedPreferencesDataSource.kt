package com.example.helloworld.data
import android.content.SharedPreferences
import com.example.helloworld.model.AppPreferencesDataSource
import com.example.helloworld.model.HelloWorldTheme

const val KEY_THEME = "KEY_THEME"
private const val THEME_DARK = 0
private const val THEME_LIGHT = 1
private const val THEME_SYSTEM = 2

class AppSharedPreferencesDataSource(private var sharedPreferences: SharedPreferences) : AppPreferencesDataSource {

    override fun setTheme(theme: HelloWorldTheme) {
        val intValue: Int
        when (theme) {
            HelloWorldTheme.Dark -> intValue = THEME_DARK
            HelloWorldTheme.Light -> intValue = THEME_LIGHT
            HelloWorldTheme.System -> intValue = THEME_SYSTEM
            else -> throw IllegalArgumentException()
        }
        sharedPreferences.edit().putInt(KEY_THEME, intValue).apply()
    }

    override fun getTheme(): HelloWorldTheme {
        val theme: HelloWorldTheme
        val intValue: Int = sharedPreferences.getInt(KEY_THEME, THEME_SYSTEM)
        theme = when (intValue) {
            THEME_DARK -> HelloWorldTheme.Dark
            THEME_LIGHT -> HelloWorldTheme.Light
            THEME_SYSTEM -> HelloWorldTheme.System
            else -> throw IllegalStateException()
        }
        return theme
    }

}