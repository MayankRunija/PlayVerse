import com.example.videoplayer.model.Video
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideoRepository {
    private val apiService = NetworkModule.apiService

    suspend fun fetchVideos(category: String): Result<List<Video>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getVideosByCategory(query = category)

                val mappedVideos = response.items.map { item ->
                    Video(
                        type = "video",
                        videoId = item.id.videoId,
                        title = item.snippet.title,
                        author = item.snippet.channelTitle,
                        publishedText = item.snippet.publishedAt.take(10),
                        thumbnailUrl = item.snippet.thumbnails.high.url
                    )
                }
                Result.success(mappedVideos)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}