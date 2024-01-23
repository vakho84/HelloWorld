package com.example.helloworld.ui.flags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.helloworld.databinding.FragmentFlagsBinding

class FlagsFragment : Fragment() {

    private var _binding: FragmentFlagsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFlagsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val austriaUrl = "https://cdn.iconscout.com/icon/free/png-512/free-austria-flag-country-nation-union-empire-32920.png?f=webp&w=512"
        val polandUrl = "https://cdn.iconscout.com/icon/free/png-512/free-poland-flag-country-nation-union-empire-33057.png?f=webp&w=512"
        val italyUrl = "https://cdn.iconscout.com/icon/free/png-512/free-italy-flag-country-nation-union-empire-32999.png?f=webp&w=512"
        val colombiaUrl = "https://cdn.iconscout.com/icon/free/png-512/free-colombia-flag-country-nation-union-empire-32946.png?f=webp&w=512"
        val madagascarUrl = "https://cdn.iconscout.com/icon/free/png-512/free-madagascar-flag-country-nation-union-empire-33042.png?f=webp&w=512"
        val thailandUrl = "https://cdn.iconscout.com/icon/free/png-512/free-thailand-flag-country-nation-union-empire-33102.png?f=webp&w=512"
        val denmarkUrl = "https://cdn.iconscout.com/icon/free/png-512/free-denmark-flag-country-nation-union-empire-32955.png?f=webp&w=512"
        val switzerlandUrl = "https://cdn.iconscout.com/icon/free/png-512/free-switzerland-flag-country-nation-union-empire-33095.png?f=webp&w=512"

        Glide.with(this).load(austriaUrl).into(binding.austriaFlag)
        Glide.with(this).load(polandUrl).into(binding.polandFlag)
        Glide.with(this).load(italyUrl).into(binding.italyFlag)
        Glide.with(this).load(colombiaUrl).into(binding.colombiaFlag)
        Glide.with(this).load(madagascarUrl).into(binding.madagascarFlag)
        Glide.with(this).load(thailandUrl).into(binding.thailandFlag)
        Glide.with(this).load(denmarkUrl).into(binding.denmarkFlag)
        Glide.with(this).load(switzerlandUrl).into(binding.switzerlandFlag)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}