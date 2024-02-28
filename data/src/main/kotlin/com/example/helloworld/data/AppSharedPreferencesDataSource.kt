package com.example.helloworld.data

import android.content.Context
import com.example.helloworld.domain.HelloWorldLang
import com.example.helloworld.domain.HelloWorldTheme

const val KEY_THEME = "KEY_THEME"
private const val THEME_DARK = 0
private const val THEME_LIGHT = 1
private const val THEME_SYSTEM = 2

const val KEY_LANG = "KEY_LANG"
private const val LANG_RU = 0
private const val LANG_ENG = 1
private const val LANG_SYSTEM = 2

internal class AppSharedPreferencesDataSource(appContext: Context) : AppPreferencesDataSource {

    private var sharedPreferences = appContext.getSharedPreferences("helloWorld", Context.MODE_PRIVATE)

    override fun setTheme(helloWorldTheme: HelloWorldTheme) {
        val intValue = when (helloWorldTheme) {
            HelloWorldTheme.Dark -> THEME_DARK
            HelloWorldTheme.Light -> THEME_LIGHT
            HelloWorldTheme.System -> THEME_SYSTEM

        }
        sharedPreferences.edit().putInt(KEY_THEME, intValue).apply()
    }

    override fun getTheme(): HelloWorldTheme {
        val helloWorldTheme: HelloWorldTheme
        val intValue = sharedPreferences.getInt(KEY_THEME, THEME_SYSTEM)
        helloWorldTheme = when (intValue) {
            THEME_DARK -> HelloWorldTheme.Dark
            THEME_LIGHT -> HelloWorldTheme.Light
            THEME_SYSTEM -> HelloWorldTheme.System
            else -> throw IllegalStateException()
        }
        return helloWorldTheme
    }

    override fun setLang(helloWorldLang: HelloWorldLang) {
        val intValue = when (helloWorldLang) {
            HelloWorldLang.Rus -> LANG_RU
            HelloWorldLang.Eng -> LANG_ENG
            HelloWorldLang.System -> LANG_SYSTEM
        }
        sharedPreferences.edit().putInt(KEY_LANG, intValue).apply()
    }

    override fun getLang(): HelloWorldLang {
        val helloWorldLang: HelloWorldLang
        val intValue = sharedPreferences.getInt(KEY_LANG, LANG_SYSTEM)
        helloWorldLang = when (intValue) {
            LANG_RU -> HelloWorldLang.Rus
            LANG_ENG -> HelloWorldLang.Eng
            THEME_SYSTEM -> HelloWorldLang.System
            else -> throw IllegalStateException()
        }
        return helloWorldLang
    }

}