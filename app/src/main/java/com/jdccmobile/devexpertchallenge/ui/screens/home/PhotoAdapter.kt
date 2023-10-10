package com.jdccmobile.devexpertchallenge.ui.screens.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jdccmobile.devexpertchallenge.R
import com.jdccmobile.devexpertchallenge.data.model.Photo
import com.jdccmobile.devexpertchallenge.databinding.ViewPhotoItemBinding
import com.squareup.picasso.Picasso

class PhotoAdapter(private val items: MutableList<Photo?>, private val favClickedListener: (Photo?) -> Unit) :
    RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_photo_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, photoPos: Int) {
        val item = items[photoPos]
        holder.bind(item)

        holder.binding.ivPhoto.setOnClickListener {
            favClickedListener(item)
        }

        if(item?.isFavorite == true) holder.binding.ivFav.visibility = View.VISIBLE
        else holder.binding.ivFav.visibility = View.GONE

    }

    fun updateData(photos: List<Photo>) {
        items.clear()
        items.addAll(photos)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val binding = ViewPhotoItemBinding.bind(view)

        fun bind(item: Photo?) {
            Picasso.get()
                .load(item?.urls)
                .into(binding.ivPhoto)
            binding.tvDescription.text = item?.description ?: "Without description"
            binding.tvUsername.text = item?.userName ?: "Error"
        }

    }
}

