package com.example.helloworld.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.helloworld.R
import com.example.helloworld.databinding.FragmentDetailsBinding
import com.example.helloworld.retrofit.ImageApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val KEY_ID = "KEY_ID"
private const val KEY_AUTHOR = "KEY_AUTHOR"
private const val KEY_WIDTH = "KEY_WIDTH"
private const val KEY_HEIGHT = "KEY_HEIGHT"
private const val KEY_URL = "KEY_URL"
private const val KEY_DOWNLOAD_URL = "KEY_DOWNLOAD_URL"

class DetailsFragment : Fragment() {

    private lateinit var detailsViewModel: DetailsViewModel

    private val imageApi: ImageApi

    private var _binding: FragmentDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://picsum.photos")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        imageApi = retrofit.create(ImageApi::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val _arguments = arguments
        val id = if (_arguments == null) null else DetailsFragmentArgs.fromBundle(_arguments).id

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        val swipe: SwipeRefreshLayout = binding.swipeRefreshLayout

        swipe.setOnRefreshListener {
            if (id == null) {
                return@setOnRefreshListener
            }

            // Update the data
            CoroutineScope(Dispatchers.IO).launch {
                loadImage(id)
            }

            // Hide swipe to refresh icon animation
            swipe.isRefreshing = false
        }

        if (id != null) {
            CoroutineScope(Dispatchers.IO).launch {
                loadImage(id)
            }
        }

        return binding.root
    }

    private suspend fun loadImage(id: Int) {
        val image = imageApi.getOneImage(id)
        activity?.runOnUiThread {
            detailsViewModel = DetailsViewModel(
                image.id,
                image.author,
                image.width,
                image.height,
                image.url,
                image.download_url
            )
            updateUi()
        }
    }

    private fun updateUi() {
        Glide.with(requireContext()).load(detailsViewModel.download_url).into(binding.fragmentDetailsImageView)
        binding.fragmentDetailsIdValue.text = detailsViewModel.id?.toString() ?: ""
        binding.fragmentDetailsAuthorValue.text = (detailsViewModel.author) ?: ""
        binding.fragmentDetailsWidthValue.text = detailsViewModel.width ?: ""
        binding.fragmentDetailsHeightValue.text = detailsViewModel.height ?: ""
        binding.fragmentDetailsUrlValue.text = detailsViewModel.url ?: ""
        binding.fragmentDetailsDownloadUrlValue.text = detailsViewModel.download_url ?: ""
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        detailsViewModel.id?.let { outState.putInt(KEY_ID, it) }
        outState.putString(KEY_AUTHOR, detailsViewModel.author)
        outState.putString(KEY_WIDTH, detailsViewModel.width)
        outState.putString(KEY_HEIGHT, detailsViewModel.height)
        outState.putString(KEY_URL, detailsViewModel.url)
        outState.putString(KEY_DOWNLOAD_URL, detailsViewModel.download_url)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}