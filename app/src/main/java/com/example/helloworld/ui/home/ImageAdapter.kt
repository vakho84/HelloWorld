package com.example.helloworld.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.helloworld.R
import com.example.helloworld.data.Image
import com.example.helloworld.databinding.ImageItemBinding

class ImageAdapter() : RecyclerView.Adapter<ImageAdapter.ImageHolder>() {
  private  val imageVmList = ArrayList<HomeViewModel>()

    class ImageHolder(item: View) : RecyclerView.ViewHolder(item) {

      private  val binding = ImageItemBinding.bind(item)

        fun bind(imageVm: HomeViewModel) = with(binding) {
            binding.tV.text = imageVm.author
            Glide.with(this.im).load(imageVm.download_url).into(binding.im)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return ImageHolder(view)
    }

    override fun getItemCount(): Int {
        return imageVmList.size
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.bind(imageVmList[position])
    }

    fun addImage(imageVm: HomeViewModel) {
        imageVmList.add(imageVm)
     // imageVmList.addAll()
        notifyDataSetChanged()
    }
}
