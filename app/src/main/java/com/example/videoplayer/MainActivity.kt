package com.example.videoplayer

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.videoplayer.ui.PlayerSheetController
import com.example.videoplayer.model.Video
import com.example.videoplayer.viewmodel.VideoViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: VideoViewModel by viewModels()
    private lateinit var playerSheetController: PlayerSheetController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI components
        playerSheetController = PlayerSheetController(findViewById(android.R.id.content))
        setupCategoryBar()

        if (savedInstanceState == null) {
            loadCategoryFragment("All")
        }
    }

    private fun setupCategoryBar() {
        val rv = findViewById<RecyclerView>(R.id.categoryRecyclerView)
        val adapter = CategoryAdapter { category -> loadCategoryFragment(category.name) }
        rv.adapter = adapter
        adapter.submitList(viewModel.getCategories())
    }

    private fun loadCategoryFragment(name: String) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.fragmentContainer, VideoListFragment.newInstance(name))
            .commit()
    }

    fun openVideoPlayer(video: Video) {
        playerSheetController.openVideo(video)
    }

    override fun onBackPressed() {
        if (!playerSheetController.handleBackPress()) {
            super.onBackPressed()
        }
    }
}