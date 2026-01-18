package com.example.videoplayer.api

import com.example.videoplayer.model.YouTubeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoApiService {
    @GET("youtube/v3/search")
    suspend fun getVideosByCategory(
        @Query("videoEmbeddable") videoEmbeddable: String = "true",
        @Query("part") part: String = "snippet",
        @Query("maxResults") maxResults: Int = 20,
        @Query("q") query: String,
        @Query("type") type: String = "video",
        @Query("key") apiKey: String = "AIzaSyBcxjUTJnH5wMmZrLtczza_TsRmlSqddEQ",

    ): YouTubeResponse
}