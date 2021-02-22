package com.learnings.github.demo.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VideoResponse (
    val kind: String? = null,
    val etag: String? = null,
    @SerializedName("items")
    @Expose
    val videos: List<Video>? = null,
    val nextPageToken: String? = null,
    val pageInfo: PageInfo? = null
)

data class PageInfo (
    val totalResults: Int? = null,
    val resultsPerPage: Int? = null
)