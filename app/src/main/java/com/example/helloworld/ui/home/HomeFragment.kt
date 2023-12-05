package com.example.helloworld.ui.home

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.helloworld.databinding.FragmentHomeBinding
import java.io.BufferedInputStream
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.homeImageView.loadFromUrl("https://all-aforizmy.ru/wp-content/uploads/2023/05/ap.jpg")

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

fun ImageView.loadFromUrl(url: String) {
    apply outer@ {
        object : Thread() {
            override fun run() {
                try {
                    URL(url).openStream().use { inputStream ->
                        BitmapFactory.decodeStream(BufferedInputStream(inputStream), null, null).let {
                            this@outer.context.mainExecutor.execute {
                                this@outer.setImageBitmap(it)
                            }
                        }
                    }
                } catch (ignored: Exception) {
                }
            }
        }.start()
    }
}