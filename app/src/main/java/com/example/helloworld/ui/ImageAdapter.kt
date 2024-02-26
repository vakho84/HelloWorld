package com.example.helloworld.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.helloworld.R
import com.example.helloworld.databinding.ImageItemBinding
import com.example.helloworld.model.ImageObjectEntity
import java.security.MessageDigest
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
               // .transform(ImageKey(imageObjectEntity.id))
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

/*
class ImageKey (val id: Int) : Transformation<Bitmap> {
    override fun updateDiskCacheKey(messageDigest: MessageDigest) {}

    override fun transform(context: Context, resource: Resource<Bitmap>, outWidth: Int, outHeight: Int): Resource<Bitmap> {
        return resource
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImageKey

        return id == other.id
    }

    override fun hashCode(): Int {
        return id
    }
}
 */