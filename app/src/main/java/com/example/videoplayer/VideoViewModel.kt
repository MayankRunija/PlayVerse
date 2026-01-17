package com.example.videoplayer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoplayer.model.Category
import com.example.videoplayer.model.Video
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class VideoViewModel : ViewModel() {

    private val _videos = MutableLiveData<List<Video>>()
    val videos: LiveData<List<Video>> = _videos

    // The Loading State
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getCategories(): List<Category> {
        return listOf(
            Category(1, "All", true),
            Category(2, "Music"),
            Category(3, "Gaming"),
            Category(4, "Live"),
            Category(5, "Tech"),
            Category(6, "Cooking")
        )
    }
    fun fetchVideosByCategory(category: String) {
        viewModelScope.launch {
            _isLoading.value = true // Start loading

            // Simulating a network delay for the eye-catchy effect
            delay(1500)

            val dummyData = List(10) { index ->
                Video("$index", "$category Video $index", "Channel $index", "1M", "1d", 0, 0)
            }

            _videos.value = dummyData
            _isLoading.value = false // Stop loading
        }
    }
}