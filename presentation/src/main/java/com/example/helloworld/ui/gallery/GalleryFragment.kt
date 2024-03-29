package com.example.helloworld.ui.gallery

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.helloworld.databinding.FragmentGalleryBinding
import java.io.BufferedInputStream
import java.net.URL

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val customUrl = "https://all-aforizmy.ru/wp-content/uploads/2023/05/ap.jpg"
//        binding.homeImageView.loadFromUrl(customUrl)
        Glide.with(this).load(customUrl).into(binding.galleryImageView)


        // edit text enter key listener
        val editTextInputUrl = binding.galleryUrlInput
        /*  editTextInputUrl.setOnKeyListener(object : View.OnKeyListener {
              override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                  // if the event is a key down event on the enter button
                  if (event.action == KeyEvent.ACTION_DOWN &&
                      keyCode == KeyEvent.KEYCODE_ENTER
                  ) {
                      // perform action on key press
                      customUrl = editTextInputUrl.getText().toString()
                      // clear focus and hide cursor from edit text
                      // editTextInputUrl.clearFocus()
                      editTextInputUrl.isCursorVisible = false
                      editTextInputUrl.imeOptions = IME_ACTION_DONE
                      return true
                  }
                  editTextInputUrl.isCursorVisible = true
                  editTextInputUrl.requestFocus()
                  return false
              }
          }) */

        val loadImage = binding.galleryLoadImageButton
        loadImage.setOnClickListener{
            Glide.with(this).load(editTextInputUrl.text.toString()).into(binding.galleryImageView)
        }

        // loadImage.setOnClickListener(View.OnClickListener {
        // binding.homeImageView.loadFromUrl(editTextInputUrl.text.toString())
        //})

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
// Alternative for Glide
fun ImageView.loadFromUrl(url: String) {
    apply outer@{
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
