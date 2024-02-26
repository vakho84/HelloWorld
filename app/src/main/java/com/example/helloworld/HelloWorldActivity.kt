package com.example.helloworld

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.helloworld.data.AppSettingsRepository
import com.example.helloworld.model.HelloWorldLang
import java.util.Locale

open class HelloWorldActivity : AppCompatActivity() {

    private lateinit var appSettingsRepository: AppSettingsRepository

    companion object {
        private fun createContext(lang: HelloWorldLang, context: Context): Context {
            val locale: Locale = when (lang) {
                HelloWorldLang.Rus -> Locale("ru")
                HelloWorldLang.Eng -> Locale("en")
                HelloWorldLang.System -> if (HelloWorldApp.isSystemLanguageRu()) Locale("ru") else Locale("en")
            }
            Locale.setDefault(locale)
            val configuration = Configuration()
            configuration.setLocale(locale)
            return context.createConfigurationContext(configuration)
        }
    }

    override fun attachBaseContext(base: Context) {
        appSettingsRepository = (base.applicationContext as HelloWorldApp).getAppSettingsRepository()
        super.attachBaseContext(createContext(appSettingsRepository.getLang(), base))
    }

     fun reconfigureActivity() {
        ActivityCompat.recreate(this)
    }
}