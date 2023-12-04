package com.example.helloworld.ui

import androidx.appcompat.app.AppCompatDelegate
import com.example.helloworld.model.HelloWorldTheme

 class HelloWorldApp {
    fun changeTheme(theme: HelloWorldTheme?) {
        val mode: Int
        mode = when (theme) {
            HelloWorldTheme.Dark -> AppCompatDelegate.MODE_NIGHT_YES
            HelloWorldTheme.Light -> AppCompatDelegate.MODE_NIGHT_NO
            HelloWorldTheme.System -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            else -> throw IllegalArgumentException()
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}