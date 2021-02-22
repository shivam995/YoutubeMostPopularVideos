package com.learnings.github.demo.data.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Video (
    val kind: String? = null,
    val etag: String? = null,
    @SerializedName("id")
    @Expose
    val videoId: String? = null,
    val snippet: Snippet? = null,
    val contentDetails: ContentDetails? = null,
    val statistics: Statistics? = null
)

data class Snippet (
    val publishedAt: String? = null,
    val channelId: String? = null,
    val title: String? = null,
    val description: String? = null,
    val thumbnails: Thumbnails? = null,
    val channelTitle: String? = null,
    val categoryId: String? = null,
    val defaultLanguage: String? = null,
    val defaultAudioLanguage: String? = null,
    val tags: List<String>? = null
    )

data class ContentDetails (
    val duration: String? = null,
    val dimension: String? = null,
    val definition: String? = null,
    var caption: String? = null
    //    @SerializedName("contentRating")
    //    @Expose
    //    public ContentRating contentRating;
)
