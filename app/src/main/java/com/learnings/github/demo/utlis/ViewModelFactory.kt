package com.learnings.github.demo.utlis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.learnings.github.demo.data.network.ApiServiceImpl
import com.learnings.github.demo.data.repository.VideoRepository
import com.learnings.github.demo.ui.viewmodel.VideoListViewModel

class ViewModelFactory(private val apiHelper: ApiServiceImpl) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VideoListViewModel::class.java)) {
            return VideoListViewModel(VideoRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}