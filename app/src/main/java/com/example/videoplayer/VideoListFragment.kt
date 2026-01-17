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
    private var categoryName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get the category name passed from MainActivity
        categoryName = arguments?.getString("ARG_CATEGORY")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_video_list, container, false)
        setupRecyclerView(view)
        return view
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.videoRecyclerView)
        videoAdapter = VideoAdapter { video ->
            (activity as? MainActivity)?.openVideoPlayer(video)
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = videoAdapter

        // --- DYNAMIC DATA LOGIC ---
        val dummyVideos = List(10) { index ->
            Video(
                "$index",
                "$categoryName Video #$index", // Title now reflects the category!
                "Channel $categoryName",
                "${(10..999).random()}K",
                "${index + 1}d",
                android.R.drawable.ic_menu_gallery,
                android.R.drawable.ic_menu_compass
            )
        }
        videoAdapter.submitList(dummyVideos)
    }

    companion object {
        fun newInstance(category: String): VideoListFragment {
            val fragment = VideoListFragment()
            val args = Bundle()
            args.putString("ARG_CATEGORY", category)
            fragment.arguments = args
            return fragment
        }
    }
}