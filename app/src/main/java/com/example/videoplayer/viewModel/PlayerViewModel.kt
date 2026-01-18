package com.example.videoplayer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.videoplayer.model.Video

class PlayerViewModel : ViewModel() {

    private val _selectedVideo = MutableLiveData<Video?>()
    val selectedVideo: LiveData<Video?> = _selectedVideo

    private val _isPlayerExpanded = MutableLiveData<Boolean>(false)
    val isPlayerExpanded: LiveData<Boolean> = _isPlayerExpanded

    fun selectVideo(video: Video) {
        _selectedVideo.value = video
        _isPlayerExpanded.value = true
    }

    fun closePlayer(): Boolean {
        return if (_isPlayerExpanded.value == true) {
            _isPlayerExpanded.value = false
            true
        } else {
            false
        }
    }

    fun setExpanded(expanded: Boolean) {
        _isPlayerExpanded.value = expanded
        if (!expanded) _selectedVideo.value = null
    }
}