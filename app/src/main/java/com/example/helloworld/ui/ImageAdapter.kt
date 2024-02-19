package com.example.helloworld.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.helloworld.R
import com.example.helloworld.databinding.ImageItemBinding
import com.example.helloworld.model.ImageObjectEntity
import java.util.function.Consumer
import java.util.function.Function

class ImageAdapter(
    val clickHandler: Consumer<Int>,
    val checkBoxClickHandler: Consumer<ImageObjectEntity>,
    val localUrlProvider: Function<ImageObjectEntity, String>
) : RecyclerView.Adapter<ImageAdapter.ImageHolder>() {
    private val imageVmList = ArrayList<ImageObjectEntity>()

    class ImageHolder(item: View, private val parent: ImageAdapter) : RecyclerView.ViewHolder(item) {
        private val binding = ImageItemBinding.bind(item)

        fun bind(imageObjectEntity: ImageObjectEntity) = with(binding) {
            binding.tV.text = imageObjectEntity.author

            val url = this@ImageHolder.parent.localUrlProvider.apply(imageObjectEntity)
            Glide.with(this@with.im)
                .load(url)
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(binding.im)

            binding.favCheckbox.isChecked = imageObjectEntity.isFavorite

            binding.im.setOnClickListener {
                this@ImageHolder.parent.clickHandler.accept(imageObjectEntity.id)
            }

            binding.favCheckbox.setOnCheckedChangeListener { _, checked ->
                this@ImageHolder.parent.checkBoxClickHandler.accept(imageObjectEntity.copy(isFavorite = checked))
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

    fun addImage(imageVm: List<ImageObjectEntity>) {
        imageVmList.clear()
        imageVmList.addAll(imageVm)
        notifyDataSetChanged()
    }
}
