package com.learnings.github.demo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.learnings.github.demo.data.model.Video
import com.learnings.github.demo.databinding.ItemviewVideoThumbnailBinding


class VideoListAdapter(private val dataList: ArrayList<Video>) :
    RecyclerView.Adapter<VideoListAdapter.ThumbnailViewHolder>() {

    private var lastAnimatedPosition: Int = 0
    var onItemClick: ((Video, ImageView) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThumbnailViewHolder {
        val binding = ItemviewVideoThumbnailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ThumbnailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ThumbnailViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
        runEnterAnimation(holder.itemView, position)
    }

    fun updateList(list: ArrayList<Video>) {
        val startCount = dataList.size
        dataList.addAll(list)
        notifyItemRangeInserted(startCount, dataList.size)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    private fun runEnterAnimation(view: View, position: Int) {
        if (position <= 2) {
            return
        }
        if (position > lastAnimatedPosition) {
            view.translationX = 1000f
            view.animate().also {
                it.translationX(0f)
                it.duration = 300
                it.interpolator = LinearInterpolator()
            }.start()
        }
    }

    inner class ThumbnailViewHolder(private val binding: ItemviewVideoThumbnailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Video) {
            binding.apply {
                video = item
                executePendingBindings()
            }

            itemView.setOnClickListener {
                onItemClick?.invoke(item, binding.thumbnail)
            }
        }
    }
}