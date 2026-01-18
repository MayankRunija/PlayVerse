package com.example.videoplayer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.videoplayer.model.Video

class PlayerViewModel : ViewModel() {

    private val _selectedVideo = MutableLiveData<Video?>()
    val selectedVideo: LiveData<Video?> = _selectedVideo

    // Track the visual state of the player sheet
    private val _isPlayerExpanded = MutableLiveData<Boolean>(false)
    val isPlayerExpanded: LiveData<Boolean> = _isPlayerExpanded

    fun selectVideo(video: Video) {
        _selectedVideo.value = video
        _isPlayerExpanded.value = true
    }

    fun closePlayer(): Boolean {
        // If the player is currently open, close it and return true to consume back press
        return if (_isPlayerExpanded.value == true) {
            _isPlayerExpanded.value = false
            // Optional: clear video data after the slide-down animation completes
            true
        } else {
            false
        }
    }

    // Call this when the user manually drags the sheet down to hide it
    fun setExpanded(expanded: Boolean) {
        _isPlayerExpanded.value = expanded
        if (!expanded) _selectedVideo.value = null
    }
}