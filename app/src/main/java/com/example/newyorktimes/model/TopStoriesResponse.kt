package com.example.newyorktimes.model

data class TopStoriesResponse(
    val status: String,
    val results: List<Story>
)

data class Story(
    val section: String,
    val title: String,
    val byline: String,
    val url: String,
    val multimedia: List<Multimedia>?
)

data class Multimedia(
    val url: String,
    val format: String
)