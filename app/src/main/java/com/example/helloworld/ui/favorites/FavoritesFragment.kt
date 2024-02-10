package com.example.helloworld.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.helloworld.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val austriaUrl = "https://cdn.iconscout.com/icon/free/png-512/free-austria-flag-country-nation-union-empire-32920.png?f=webp&w=512"
        val polandUrl = "https://cdn.iconscout.com/icon/free/png-512/free-poland-flag-country-nation-union-empire-33057.png?f=webp&w=512"
        val italyUrl = "https://cdn.iconscout.com/icon/free/png-512/free-italy-flag-country-nation-union-empire-32999.png?f=webp&w=512"
        val colombiaUrl = "https://cdn.iconscout.com/icon/free/png-512/free-colombia-flag-country-nation-union-empire-32946.png?f=webp&w=512"
        val madagascarUrl = "https://cdn.iconscout.com/icon/free/png-512/free-madagascar-flag-country-nation-union-empire-33042.png?f=webp&w=512"
        val thailandUrl = "https://cdn.iconscout.com/icon/free/png-512/free-thailand-flag-country-nation-union-empire-33102.png?f=webp&w=512"
        val denmarkUrl = "https://cdn.iconscout.com/icon/free/png-512/free-denmark-flag-country-nation-union-empire-32955.png?f=webp&w=512"
        val switzerlandUrl = "https://cdn.iconscout.com/icon/free/png-512/free-switzerland-flag-country-nation-union-empire-33095.png?f=webp&w=512"


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}