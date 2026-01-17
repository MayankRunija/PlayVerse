package com.example.videoplayer.ui

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.videoplayer.R
import com.example.videoplayer.model.Video
import com.google.android.material.bottomsheet.BottomSheetBehavior

class PlayerSheetController(private val root: View) {

    private val bottomSheet: ConstraintLayout = root.findViewById(R.id.bottomSheetRoot)
    private val behavior: BottomSheetBehavior<ConstraintLayout> = BottomSheetBehavior.from(bottomSheet)

    private val title: TextView = bottomSheet.findViewById(R.id.sheetVideoTitle)
    private val desc: TextView = bottomSheet.findViewById(R.id.sheetVideoDesc)

    init {
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
        behavior.peekHeight = 0
    }

    fun openVideo(video: Video) {
        title.text = video.title
        desc.text = "${video.channelName} • ${video.views} views"
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    fun handleBackPress(): Boolean {
        if (behavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.state = BottomSheetBehavior.STATE_HIDDEN
            return true
        }
        return false
    }
}