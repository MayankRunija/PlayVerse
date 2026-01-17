package com.example.videoplayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.videoplayer.model.Video

class VideoListFragment : Fragment() {

    private lateinit var videoAdapter: VideoAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_video_list, container, false)

        setupRecyclerView(view)
        return view
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.videoRecyclerView)
        videoAdapter = VideoAdapter { video ->
            // This is where the Bottom Sheet will be triggered
            (activity as? MainActivity)?.openVideoPlayer(video)
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = videoAdapter

        // Dummy data for visual testing
        val dummyVideos = List(10) {
            Video(
                "1",
                "How to build a Beautiful App",
                "DesignCode",
                "500K",
                "1d",
                android.R.drawable.ic_menu_gallery,
                android.R.drawable.ic_menu_compass
            )
        }
        videoAdapter.submitList(dummyVideos)
    }
}