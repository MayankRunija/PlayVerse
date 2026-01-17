package com.example.videoplayer.model

data class Video(
    val id: String,
    val title: String,
    val channelName: String,
    val views: String,
    val timeAgo: String,
    val thumbnailResId: Int, // In a real app, this would be a URL string
    val channelAvatarResId: Int
)