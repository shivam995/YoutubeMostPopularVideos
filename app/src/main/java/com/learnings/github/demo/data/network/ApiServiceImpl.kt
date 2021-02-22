package com.learnings.github.demo.data.network


class ApiServiceImpl(private val apiService: ApiService) {

    suspend fun mostPopularVideos(queryMap: HashMap<String, String>) =
        apiService.mostPopularVideos(queryMap)


}