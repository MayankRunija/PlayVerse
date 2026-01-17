package com.example.videoplayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.videoplayer.viewmodel.VideoViewModel

class VideoListFragment : Fragment() {

    private lateinit var videoAdapter: VideoAdapter
    // Shared ViewModel with Activity ensures data persistence during fragment swaps
    private val viewModel: VideoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_video_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loadingBar = view.findViewById<ProgressBar>(R.id.loadingBar)
        val recyclerView = view.findViewById<RecyclerView>(R.id.videoRecyclerView)

        setupRecyclerView(view)

        // 1. Get the category name passed from the Bundle
        val category = arguments?.getString("ARG_CATEGORY") ?: "All"

        // 2. TRIGGER THE DATA FETCH (This removes your "never used" warning)
        viewModel.fetchVideosByCategory(category)

        // 3. Observe the Loading State for the loader
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            loadingBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            recyclerView.visibility = if (isLoading) View.GONE else View.VISIBLE
        }

        // 4. Observe the Video Data
        viewModel.videos.observe(viewLifecycleOwner) { videoList ->
            videoAdapter.submitList(videoList)
        }
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.videoRecyclerView)
        videoAdapter = VideoAdapter { video ->
            (activity as? MainActivity)?.openVideoPlayer(video)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = videoAdapter
    }

    companion object {
        fun newInstance(category: String): VideoListFragment {
            return VideoListFragment().apply {
                arguments = Bundle().apply {
                    putString("ARG_CATEGORY", category)
                }
            }
        }
    }
}