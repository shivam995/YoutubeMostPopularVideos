package com.learnings.github.demo.data.repository

import com.learnings.github.demo.BuildConfig
import com.learnings.github.demo.data.model.VideoResponse
import com.learnings.github.demo.data.network.ApiServiceImpl
import com.learnings.github.demo.ui.viewmodel.VideoListViewModel


class VideoRepository(private val apiHelper: ApiServiceImpl) {

    suspend fun getPopularVideos(
        maxResult: Int = VideoListViewModel.VIDEO_LIST_PAGE_ITEM_COUNT,
        nextPageToken: String? = null
    ): VideoResponse {

        val queryMap = HashMap<String, String>()
        queryMap["maxResults"] = "$maxResult"
        queryMap["key"] = BuildConfig.API_KEY
        if (!nextPageToken.isNullOrEmpty()) {
            queryMap["pageToken"] = nextPageToken
        }
        return apiHelper.mostPopularVideos(queryMap)
    }
}