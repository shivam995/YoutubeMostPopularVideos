package com.learnings.github.demo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.learnings.github.demo.data.repository.VideoRepository
import com.learnings.github.demo.utlis.Resource
import kotlinx.coroutines.Dispatchers

class VideoListViewModel(private val videoRepository: VideoRepository): ViewModel() {
    companion object {
        val VIDEO_LIST_PAGE_ITEM_COUNT = 10
    }


    fun popularVideos(nextPageToken: String? = null) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = videoRepository.getPopularVideos(nextPageToken = nextPageToken)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}