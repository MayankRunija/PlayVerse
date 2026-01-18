package com.example.videoplayer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.videoplayer.R
import com.example.videoplayer.adapter.VideoAdapter
import com.example.videoplayer.viewModel.VideoViewModel
import com.example.videoplayer.viewmodel.PlayerViewModel

class VideoListFragment : Fragment() {

    private lateinit var videoAdapter: VideoAdapter

    // Shared Data ViewModel (for the list)
    private val videoViewModel: VideoViewModel by activityViewModels()

    // Shared Player ViewModel (to trigger the bottom sheet)
    private val playerViewModel: PlayerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_video_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(view)

        val category = arguments?.getString("ARG_CATEGORY") ?: "All"
        videoViewModel.fetchVideosByCategory(category)

        // Observe the Loading State
        videoViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            val shimmerLayout = view.findViewById<com.facebook.shimmer.ShimmerFrameLayout>(R.id.shimmerLayout)
            val recyclerView = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.videoRecyclerView)

            if (isLoading) {
                shimmerLayout.visibility = View.VISIBLE
                shimmerLayout.startShimmer()
                recyclerView.visibility = View.GONE
            } else {
                shimmerLayout.stopShimmer()
                shimmerLayout.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
        }

        // Observe the Video Data
        videoViewModel.videos.observe(viewLifecycleOwner) { videoList ->
            videoAdapter.submitList(videoList)
        }
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.videoRecyclerView)

        // Use the PlayerViewModel to select the video
        videoAdapter = VideoAdapter { video ->
            playerViewModel.selectVideo(video)
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