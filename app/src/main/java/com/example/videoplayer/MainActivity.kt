package com.example.videoplayer

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.core.graphics.toColorInt
import com.example.videoplayer.adapter.CategoryAdapter
import com.example.videoplayer.ui.VideoListFragment
import com.example.videoplayer.ui.VideoDetailSheet
import com.example.videoplayer.viewModel.VideoViewModel
import com.example.videoplayer.viewmodel.PlayerViewModel


class MainActivity : AppCompatActivity() {

    private val videoViewModel: VideoViewModel by viewModels()
    private val playerViewModel: PlayerViewModel by viewModels()

    private lateinit var detailSheet: VideoDetailSheet

    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = "#0F0F0F".toColorInt()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomSheetView = findViewById<View>(R.id.bottomSheetInclude)
        detailSheet = VideoDetailSheet(
            sheetView = bottomSheetView,
            viewModel = playerViewModel,
            lifecycleOwner = this
        )

        setupCategoryBar()

        if (savedInstanceState == null) {
            loadCategoryFragment("All")
        }
    }

    private fun setupCategoryBar() {
        val rv = findViewById<RecyclerView>(R.id.categoryRecyclerView)
        val adapter = CategoryAdapter { category ->
            loadCategoryFragment(category.name)
        }
        rv.adapter = adapter
        adapter.submitList(videoViewModel.getCategories())
    }

    private fun loadCategoryFragment(name: String) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.fragmentContainer, VideoListFragment.newInstance(name))
            .commit()
    }

    override fun onBackPressed() {
        if (!playerViewModel.closePlayer()) {
            super.onBackPressed()
        }
    }
}