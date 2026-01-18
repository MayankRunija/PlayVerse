package com.example.videoplayer.model
data class YouTubeResponse(val items: List<YouTubeItem>)
data class YouTubeItem(val id: VideoId, val snippet: Snippet)
data class VideoId(val videoId: String)
data class Snippet(
    val title: String,
    val channelTitle: String,
    val publishedAt: String,
    val thumbnails: Thumbnails
)
data class Thumbnails(val high: ThumbnailDetails)
data class ThumbnailDetails(val url: String)

data class Video(
    val type: String,
    val videoId: String,
    val title: String,
    val author: String,
    val publishedText: String,
    val thumbnailUrl: String
)