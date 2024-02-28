package com.example.helloworld.ui.about

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import com.example.helloworld.HelloWorldActivity
import com.example.helloworld.HelloWorldApp
import com.example.helloworld.databinding.FragmentAboutBinding
import com.example.helloworld.domain.AppSettingsRepository
import com.example.helloworld.domain.HelloWorldLang
import com.example.helloworld.domain.HelloWorldTheme

class AboutFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null
    private lateinit  var appSettingsRepository: AppSettingsRepository

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val onCheckedChangeListenerTheme = CompoundButton.OnCheckedChangeListener { _, isChecked ->
        val theme: HelloWorldTheme = if (isChecked) HelloWorldTheme.Dark else HelloWorldTheme.Light
        appSettingsRepository.setTheme(theme)
        HelloWorldApp.changeTheme(theme)
    }

    private val onCheckedChangeListenerLang = CompoundButton.OnCheckedChangeListener { _, isChecked ->
        val lang: HelloWorldLang = if (isChecked) HelloWorldLang.Rus else HelloWorldLang.Eng
        appSettingsRepository.setLang(lang)
        (activity as HelloWorldActivity?)?.recreate()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        appSettingsRepository = (requireContext().applicationContext as HelloWorldApp).appSettingsRepository

        _binding = FragmentAboutBinding.inflate(inflater, container, false)

        binding.phoneAbout.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:+75555555555")
            startActivity(intent)
        }

        binding.websiteAbout.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://kotlinlang.org")
            startActivity(intent)
        }

        binding.emailAbout.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:test@gmail.com")
            startActivity(intent)
        }

        updateThemeSwitch()
        binding.themeSwitchCompat.setOnCheckedChangeListener(onCheckedChangeListenerTheme)

        updateLangSwitch()
        binding.langSwitchCompat.setOnCheckedChangeListener(onCheckedChangeListenerLang)

        binding.aboutSystemDefaultButton.setOnClickListener {
            appSettingsRepository.setTheme(HelloWorldTheme.System)
            HelloWorldApp.changeTheme(HelloWorldTheme.System)
            updateThemeSwitch()
            appSettingsRepository.setLang(HelloWorldLang.System)
            (activity as HelloWorldActivity).reconfigureActivity()
            updateLangSwitch()
        }

        return binding.root
    }

    private fun updateThemeSwitch() {
        val isNightModeOn: Boolean
        val theme: HelloWorldTheme = appSettingsRepository.getTheme()
        isNightModeOn = when (theme) {
            HelloWorldTheme.Dark -> true
            HelloWorldTheme.Light -> false
            HelloWorldTheme.System -> {
                val currentNightMode = requireContext().applicationContext.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                currentNightMode == Configuration.UI_MODE_NIGHT_YES
            }
        }
        binding.themeSwitchCompat.isChecked = isNightModeOn
    }

    private fun updateLangSwitch() {
        val isRuOn: Boolean
        val lang: HelloWorldLang = appSettingsRepository.getLang()
        isRuOn = when(lang) {
            HelloWorldLang.Rus -> true
            HelloWorldLang.Eng -> false
            HelloWorldLang.System ->  HelloWorldApp.isSystemLanguageRu()
        }
        binding.langSwitchCompat.isChecked = isRuOn
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}