package com.example.videoplayer.ui

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import com.example.videoplayer.R
import com.example.videoplayer.viewmodel.PlayerViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior

class VideoDetailSheet(
    private val root: View,
    private val viewModel: PlayerViewModel,
    private val lifecycleOwner: LifecycleOwner
) {
    // 1. Initialize Views from the included layout
    private val bottomSheet: ConstraintLayout = root.findViewById(R.id.bottomSheetRoot)
    private val behavior: BottomSheetBehavior<ConstraintLayout> = BottomSheetBehavior.from(bottomSheet)

    private val title: TextView = root.findViewById(R.id.sheetVideoTitle)
    private val desc: TextView = root.findViewById(R.id.sheetVideoDesc)
    private val channelName: TextView = root.findViewById(R.id.sheetChannelName)
    private val subscribeBtn: AppCompatButton = root.findViewById(R.id.subscribeButton)

    init {
        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        // 2. Watch for Video Changes
        viewModel.selectedVideo.observe(lifecycleOwner) { video ->
            video?.let {
                title.text = it.title
                desc.text = "${it.views} views"
                channelName.text = it.channelName
                // You could also load the channel avatar here using Glide
            }
        }

        // 3. Watch for Visibility Changes (The "Eye-Catchy" slide)
        viewModel.isSheetVisible.observe(lifecycleOwner) { isVisible ->
            behavior.state = if (isVisible) {
                BottomSheetBehavior.STATE_EXPANDED
            } else {
                BottomSheetBehavior.STATE_HIDDEN
            }
        }
    }

    private fun setupListeners() {
        // Sync behavior with ViewModel if user drags it down manually
        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    viewModel.closePlayer()
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

        subscribeBtn.setOnClickListener {
            // Handle subscribe logic
        }
    }
}