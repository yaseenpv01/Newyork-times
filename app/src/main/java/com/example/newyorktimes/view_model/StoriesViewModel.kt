package com.example.newyorktimes.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newyorktimes.api_service.StoriesRepository
import com.example.newyorktimes.model.Story
import kotlinx.coroutines.launch

class StoriesViewModel(private val repository: StoriesRepository) : ViewModel() {

    private val _stories = MutableLiveData<List<Story>>()
    val stories: LiveData<List<Story>> get() = _stories

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private var allStories: List<Story> = emptyList()

    fun fetchTopStories(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = repository.getTopStories(apiKey)
                allStories = response.results
                _stories.value = allStories
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun searchStories(query: String) {
        _stories.value = allStories.filter {
            it.title.contains(query, ignoreCase = true) || it.byline.contains(query, ignoreCase = true)
        }
    }

    fun filterStoriesBySection(section: String) {
        if (section == "All") {
            _stories.value = allStories
        } else {
            _stories.value = allStories.filter { it.section.equals(section, ignoreCase = true) }
        }
    }


}
