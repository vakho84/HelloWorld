package com.example.helloworld.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.helloworld.R
import com.example.helloworld.data.ImageObject
import com.example.helloworld.databinding.ImageItemBinding
import java.util.function.Consumer

class ImageAdapter(val clickHandler: Consumer<Int>) : RecyclerView.Adapter<ImageAdapter.ImageHolder>() {
    private val imageVmList = ArrayList<ImageObject>()

    class ImageHolder(item: View, val parent: ImageAdapter) : RecyclerView.ViewHolder(item) {
        private  val binding = ImageItemBinding.bind(item)

        fun bind(imageVm: ImageObject) = with(binding) {
            binding.tV.text = imageVm.author
            Glide.with(this.im).load(imageVm.download_url).into(binding.im)

            binding.im.setOnClickListener {
                val clickedImageId: Int = imageVm.id
                this@ImageHolder.parent.clickHandler.accept(clickedImageId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return ImageHolder(view, this)
    }

    override fun getItemCount(): Int {
        return imageVmList.size
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.bind(imageVmList[position])
    }

   /* fun addImage(imageVm: HomeViewModel) {
        imageVmList.add(imageVm)
        notifyDataSetChanged()
    }
   */


    fun addImage(imageVm: ArrayList<ImageObject>) {
        imageVmList.clear()
        imageVmList.addAll(imageVm)
        notifyDataSetChanged()
    }

}
