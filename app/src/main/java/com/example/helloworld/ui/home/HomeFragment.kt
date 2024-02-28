package com.example.helloworld.ui.home

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
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    private var _binding: FragmentImageListBinding? = null

    private val homeViewModelInstance: HomeViewModel by viewModels { HomeViewModel.Factory }

    private val binding get() = _binding!!

    private val adapter = ImageAdapter(
        { findNavController().navigate(HomeFragmentDirections.actionHomeToDetails(it)) },
        { imageObject ->
            CoroutineScope(Dispatchers.IO).launch {
                homeViewModelInstance.update(imageObject)
            }
        },
        { it.downloadUrl }
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

        swipe.setOnRefreshListener {
            CoroutineScope(Dispatchers.IO).launch {
                homeViewModelInstance.refresh()

                withContext(Dispatchers.Main) {
                    swipe.isRefreshing = false
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModelInstance.getList().observe(viewLifecycleOwner) { adapter.addImage(it) }

        CoroutineScope(Dispatchers.IO).launch {
            homeViewModelInstance.refresh()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}