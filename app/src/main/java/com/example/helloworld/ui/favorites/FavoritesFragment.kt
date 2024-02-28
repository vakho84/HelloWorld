package com.example.helloworld.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helloworld.databinding.FragmentImageListBinding
import com.example.helloworld.ui.ImageAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment() {
    private var _binding: FragmentImageListBinding? = null

    private val favoritesViewModel: FavoritesViewModel by viewModels { FavoritesViewModel.Factory }

    private val binding get() = _binding!!

    private val adapter = ImageAdapter(
        { findNavController().navigate(FavoritesFragmentDirections.actionFavoritesToDetails(it)) },
        { imageObject -> CoroutineScope(Dispatchers.IO).launch {
            favoritesViewModel.update(imageObject)
        } },
        { favoritesViewModel.getUrl(it.id, it.downloadUrl) }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageListBinding.inflate(inflater, container, false)

        val swipe = binding.homeSwipeRefreshLayout
        val homeRecyclerView = binding.homeRecyclerView

        homeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        homeRecyclerView.adapter = adapter

        swipe.isEnabled = false

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoritesViewModel.getList().observe(viewLifecycleOwner) { adapter.addImage(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}