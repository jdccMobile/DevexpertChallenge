package com.jdccmobile.devexpertchallenge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jdccmobile.devexpertchallenge.data.model.UnsplashPhotoItem
import com.jdccmobile.devexpertchallenge.data.model.UnsplashPhotosResult
import com.jdccmobile.devexpertchallenge.databinding.ViewPhotoItemBinding
import com.squareup.picasso.Picasso

class PhotoAdapter(private val items: UnsplashPhotosResult?) :
    RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewPhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: ViewPhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UnsplashPhotoItem?) {
            Picasso.get()
                .load(item?.urls?.small)
                .into(binding.ivPhoto)
            binding.tvDescription.text = item?.description ?: "Without description"
            binding.tvUsername.text = item?.user?.username ?: "Error"

        }
    }
}

