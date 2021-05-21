package com.droid.camera.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.droid.camera.R

/**
 * Created by SARATH on 21-05-2021
 */
class ImageAdapter(private val context: Context , val mutableList: MutableList<Uri>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>(){

    inner class ImageViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var image : ImageView = itemView.findViewById(R.id.ivimage)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageAdapter.ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.image,parent,false)

        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageAdapter.ImageViewHolder, position: Int) {
        val currentImage = mutableList[position]
        Glide.with(context).load(currentImage).centerCrop().into(holder.image)

    }

    override fun getItemCount(): Int {
        return mutableList.size
    }

}