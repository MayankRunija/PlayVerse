package com.example.videoplayer.ui

import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import com.example.videoplayer.R
import com.example.videoplayer.model.Video
import com.example.videoplayer.viewmodel.PlayerViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class VideoDetailSheet(
    private val sheetView: View, // This is the 'bottomSheetInclude' from MainActivity
    private val viewModel: PlayerViewModel,
    lifecycleOwner: LifecycleOwner
) {
    private val behavior = BottomSheetBehavior.from(sheetView)

    // Use sheetView.findViewById to ensure we are looking inside the sheet!
    private val titleView: TextView = sheetView.findViewById(R.id.sheetVideoTitle)
    private val descView: TextView = sheetView.findViewById(R.id.sheetVideoDesc)
    private val channelNameView: TextView = sheetView.findViewById(R.id.sheetChannelName)
    private val youtubePlayerView: YouTubePlayerView = sheetView.findViewById(R.id.youtube_player_view)
    private var activePlayer: YouTubePlayer? = null
    init {
        // Essential: Keeps player in sync with Activity/Fragment lifecycle
        lifecycleOwner.lifecycle.addObserver(youtubePlayerView)

        viewModel.selectedVideo.observe(lifecycleOwner) { video ->
            video?.let {
                bindVideoData(it)
                loadYouTubeVideo(it.videoId)
            }
        }

        viewModel.isPlayerExpanded.observe(lifecycleOwner) { isExpanded ->
            behavior.state = if (isExpanded) {
                BottomSheetBehavior.STATE_EXPANDED
            } else {
                activePlayer?.pause() // Stop audio when hidden
                BottomSheetBehavior.STATE_HIDDEN
            }
        }

        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    viewModel.setExpanded(false)
                    activePlayer?.pause()
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
    }

    private fun bindVideoData(video: Video) {
        titleView.text = video.title
        descView.text = "${video.viewCountText} • ${video.publishedText}"
        channelNameView.text = video.author
    }

    private fun loadYouTubeVideo(videoId: String) {
        if (activePlayer != null) {
            activePlayer?.loadVideo(videoId, 0f)
        } else {
            val options = IFramePlayerOptions.Builder()
                .controls(1)
                .fullscreen(0)
                // Add these two to help bypass some embedding restrictions
                .rel(0) // Don't show related videos from other channels
                .ivLoadPolicy(3) // Hide video annotations
                .build()

            youtubePlayerView.initialize(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    activePlayer = youTubePlayer
                    youTubePlayer.loadVideo(videoId, 0f)
                }

                override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
                    if (error == PlayerConstants.PlayerError.VIDEO_NOT_PLAYABLE_IN_EMBEDDED_PLAYER) {
                        titleView.text = "Playback Restricted by YouTube"
                    }
                }
            }, options)
        }
    }
}