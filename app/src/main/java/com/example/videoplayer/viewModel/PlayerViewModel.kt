package com.example.videoplayer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.videoplayer.model.Video

class PlayerViewModel : ViewModel() {

    // The current video being played
    private val _selectedVideo = MutableLiveData<Video?>()
    val selectedVideo: LiveData<Video?> = _selectedVideo

    // State of the player (Expanded, Hidden, etc.)
    private val _isSheetVisible = MutableLiveData<Boolean>(false)
    val isSheetVisible: LiveData<Boolean> = _isSheetVisible

    fun selectVideo(video: Video) {
        _selectedVideo.value = video
        _isSheetVisible.value = true
    }

    fun closePlayer(): Boolean {
        if (_isSheetVisible.value == true) {
            _isSheetVisible.value = false
            return true
        }
        return false
    }
}