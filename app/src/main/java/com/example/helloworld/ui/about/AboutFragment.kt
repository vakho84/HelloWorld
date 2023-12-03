package com.example.helloworld.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.helloworld.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {


        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.phoneAbout.setOnClickListener {v->
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:+75555555555")
            startActivity(intent)
        }

        binding.websiteAbout.setOnClickListener{v->
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://kotlinlang.org")
            startActivity(intent)
        }

        binding.emailAbout.setOnClickListener { v->
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:test@gmail.com")
            startActivity(intent)
        }

        val themeSwitchCompat = binding.themeSwitchCompat
        val langSwitchCompat = binding.langSwitchCompat

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}