package com.learnings.github.demo.data.network

import com.learnings.github.demo.data.model.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {


    @GET("v3/videos?part=snippet%2CcontentDetails%2Cstatistics&chart=mostPopular&regionCode=IN")
    suspend fun mostPopularVideos(@QueryMap options: Map<String, String>) : VideoResponse

}