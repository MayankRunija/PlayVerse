package com.example.videoplayer.viewModel

import VideoRepository
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoplayer.model.Category
import com.example.videoplayer.model.Video
import kotlinx.coroutines.launch

class VideoViewModel : ViewModel() {

    // 1. Explicitly initialize the repository
    private val repository = VideoRepository()

    private val _videos = MutableLiveData<List<Video>>()
    val videos: LiveData<List<Video>> = _videos

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

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
            _isLoading.value = true
            _errorMessage.value = null

            val result = repository.fetchVideos(category)

            // 2. We explicitly define the type (videoList: List<Video>)
            // to fix the "Cannot infer type" and "Ambiguity" errors.
            result.onSuccess { videoList: List<Video> ->
                // This will print the number of videos received to the console
                Log.d("API_RESPONSE", "Successfully fetched ${videoList.size} videos for category: $category")

                // This will print each video title
                videoList.forEach { video ->
                    Log.d("API_RESPONSE", "Video: ${video.title}")
                }

                _videos.value = videoList.filter { it.type == "video" }
            }

            _isLoading.value = false
        }
    }
}