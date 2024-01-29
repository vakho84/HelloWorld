package com.example.helloworld.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.helloworld.R
import com.example.helloworld.data.ImageObject
import com.example.helloworld.databinding.FragmentHomeBinding
import com.example.helloworld.retrofit.ImageListApi
import com.example.helloworld.ui.details.DetailsFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.function.Consumer


private const val KEY_AUTHOR = "KEY_AUTHOR"
private const val KEY_DOWNLOAD_URL = "KEY_DOWNLOAD_URL"


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // private val imageApi: ImageApi
    private val imageListApi: ImageListApi
    private val limit = "5"
    private val page = "30"

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var listOfImageObjects: ArrayList<ImageObject>

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val adapter = ImageAdapter {
         findNavController().navigate(HomeFragmentDirections.actionHomeToDetails(it))
    }

    /*  init {
          val retrofit = Retrofit.Builder()
              .baseUrl("https://picsum.photos")
              .addConverterFactory(GsonConverterFactory.create())
              .build()
          imageApi = retrofit.create(ImageApi::class.java)
      }
   */
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://picsum.photos")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        imageListApi = retrofit.create(ImageListApi::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        homeViewModel = if (savedInstanceState == null) {
            HomeViewModel(
                null,
                null,
            )
        } else {
            HomeViewModel(
                savedInstanceState.getString(KEY_AUTHOR),
                savedInstanceState.getString(KEY_DOWNLOAD_URL)!!,
            )
        }

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val swipe: SwipeRefreshLayout = binding.homeSwipeRefreshLayout


        CoroutineScope(Dispatchers.IO).launch {
            val imageList = imageListApi.getImageList(page, limit)
            activity?.runOnUiThread {
                val arraySize = imageList.size
                listOfImageObjects = ArrayList(arraySize)
                //  listOfImageObjects.removeAll(imageList)

                listOfImageObjects.addAll(0, imageList)
                initHomeRecyclerView()

            }
        }


        /*     swipe.setOnRefreshListener {
                 // Update the data
                 CoroutineScope(Dispatchers.IO).launch {
                     val image = imageApi.getOneImage(i)
                     i+=10
                     activity?.runOnUiThread {
                         homeViewModel = HomeViewModel(
                             image.author,
                             image.download_url
                         )
                         initHomeRecyclerView()
                     }
                 }
                 // Hide swipe to refresh icon animation
                 swipe.isRefreshing = false
             }
     */
        swipe.setOnRefreshListener {
            // Update the data
            CoroutineScope(Dispatchers.IO).launch {
                val imageList = imageListApi.getImageList(page, limit)
                activity?.runOnUiThread {
                    val arraySize = imageList.size
                    listOfImageObjects = ArrayList(arraySize)
                    //  listOfImageObjects.removeAll(imageList)

                    listOfImageObjects.addAll(0, imageList)
                    initHomeRecyclerView()

                }
            }
            // Hide swipe to refresh icon animation
            swipe.isRefreshing = false
        }

        return root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_AUTHOR, homeViewModel.author)
        outState.putString(KEY_DOWNLOAD_URL, homeViewModel.download_url)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initHomeRecyclerView() {
        binding.apply {
            homeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            homeRecyclerView.adapter = adapter

            //    val imageVm = HomeViewModel(homeViewModel.author, homeViewModel.download_url)
            //    var imageVmList: HomeViewModelList
            //    adapter.addImage(imageVm)

            adapter.addImage(listOfImageObjects)

        }
    }

}