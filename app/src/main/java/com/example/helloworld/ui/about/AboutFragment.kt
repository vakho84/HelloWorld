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
import com.example.helloworld.databinding.FragmentAboutBinding
import com.example.helloworld.model.AppSettingsRepository
import com.example.helloworld.model.HelloWorldTheme
import com.example.helloworld.ui.HelloWorldApp

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private lateinit  var appSettingsRepository: AppSettingsRepository

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

         private val onCheckedChangeListenerTheme = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            val theme: HelloWorldTheme = if (isChecked) HelloWorldTheme.Dark else HelloWorldTheme.Light
            appSettingsRepository.setTheme(theme)
            HelloWorldApp.changeTheme(theme)
        }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        appSettingsRepository = (requireContext().applicationContext as HelloWorldApp).getAppSettingsRepository()

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

        val themeSwitchCompat = binding.themeSwitchCompat
        val langSwitchCompat = binding.langSwitchCompat

        updateThemeSwitch()
        themeSwitchCompat.setOnCheckedChangeListener(onCheckedChangeListenerTheme)

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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}