package com.example.helloworld.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.helloworld.databinding.FragmentDetailsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsFragment : Fragment() {
    private val detailsViewModel: DetailsViewModel by viewModels { DetailsViewModel.Factory }

    private var _binding: FragmentDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
            CoroutineScope(Dispatchers.IO).launch {
                id?.let { detailsViewModel.refresh(it) }

                withContext(Dispatchers.Main) {
                    swipe.isRefreshing = false
                }
            }
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val _arguments = arguments
        val id = if (_arguments == null) null else DetailsFragmentArgs.fromBundle(_arguments).id

        detailsViewModel.load(id).observe(viewLifecycleOwner) {
            Glide.with(requireContext()).load(it.downloadUrl).into(binding.fragmentDetailsImageView)
            binding.fragmentDetailsIdValue.text = it.id.toString()
            binding.fragmentDetailsAuthorValue.text = (it.author)
            binding.fragmentDetailsWidthValue.text = it.width
            binding.fragmentDetailsHeightValue.text = it.height
            binding.fragmentDetailsUrlValue.text = it.url
            binding.fragmentDetailsDownloadUrlValue.text = it.downloadUrl
        }

        CoroutineScope(Dispatchers.IO).launch {
            id?.let { detailsViewModel.refresh(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}