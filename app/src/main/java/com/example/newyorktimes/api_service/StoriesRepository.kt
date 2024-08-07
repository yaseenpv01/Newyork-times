package com.example.newyorktimes.api_service

import com.example.newyorktimes.model.TopStoriesResponse

class StoriesRepository {

    private val api = RetrofitInstance.api
    suspend fun getTopStories(apiKey: String): TopStoriesResponse {

        return api.getTopStories(apiKey)
    }

}