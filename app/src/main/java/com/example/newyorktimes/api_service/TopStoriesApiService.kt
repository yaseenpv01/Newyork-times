package com.example.newyorktimes.api_service

import com.example.newyorktimes.model.TopStoriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TopStoriesApiService {

    @GET("topstories/v2/home.json")
    suspend fun getTopStories(@Query("api-key") apiKey: String): TopStoriesResponse


}