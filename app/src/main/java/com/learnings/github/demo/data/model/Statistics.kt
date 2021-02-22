package com.learnings.github.demo.data.model

import android.annotation.SuppressLint
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.youtube.player.YouTubeThumbnailView
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.lang.Exception

data class Statistics(
    val viewCount: String? = null,
    val likeCount: String? = null,
    val dislikeCount: String? = null,
    val favoriteCount: String? = null,
    val commentCount: String? = null
)

data class ThumbnailDimension (
    val url: String? = null,
    val width: Int? = null,
    val height: Int? = null
)

data class Thumbnails (
    @SerializedName("default")
    @Expose
    val _default: ThumbnailDimension? = null,
    val medium: ThumbnailDimension? = null,
    val high: ThumbnailDimension? = null,
    val standard: ThumbnailDimension? = null
) {
    companion object {
        private val THUMBNAIL_BASE_URL = "http://img.youtube.com/vi/"
        @SuppressLint("CheckResult")
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: YouTubeThumbnailView, videoId: String?) {
           try {
               val imageUrl = "${THUMBNAIL_BASE_URL}${videoId}/0.jpg"
               Glide.with(view.context)
                   .load(imageUrl)
                   .into(view)
           }catch (e: Exception){
               e.printStackTrace()
           }

        }

    }
}
