package com.example.videoplayer

import android.os.Bundle
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import androidx.recyclerview.widget.RecyclerView
import com.example.videoplayer.model.Category
import com.example.videoplayer.model.Video

class MainActivity : AppCompatActivity() {

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private lateinit var sheetTitle: TextView
    private lateinit var sheetDesc: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupCategories()
        setupBottomSheet() // Initialize the sheet logic

        if (savedInstanceState == null) {
            loadCategoryFragment("All")
        }
    }

    private fun setupBottomSheet() {
            // Make sure 'bottomSheetRoot' is the ID of the ConstraintLayout
            // INSIDE your layout_video_bottom_sheet.xml file.
            val bottomSheet = findViewById<ConstraintLayout>(R.id.bottomSheetRoot)

            if (bottomSheet == null) {
                // This is a safety check to help you debug
                android.util.Log.e("VideoApp", "Bottom Sheet view not found! Check your IDs.")
                return
            }

            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        bottomSheetBehavior.peekHeight = 0
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        // 2. Find the views inside the bottomSheet container
        sheetTitle = bottomSheet.findViewById(R.id.sheetVideoTitle)
        sheetDesc = bottomSheet.findViewById(R.id.sheetVideoDesc)
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            // Adding '?' to View makes it nullable, which usually matches the library signature
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    // Logic for hidden state
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // Logic for sliding
            }
        })
    }

    private fun setupCategories() {
        val rv = findViewById<RecyclerView>(R.id.categoryRecyclerView)
        categoryAdapter = CategoryAdapter { category ->
            // Logic to swap fragments based on category
            loadCategoryFragment(category.name)
        }
        rv.adapter = categoryAdapter

        // Dummy Data for now
        val list = listOf(
            Category(1, "All", true),
            Category(2, "Music"),
            Category(3, "Gaming"),
            Category(4, "Live"),
            Category(5, "Tech"),
            Category(6, "Cooking")
        )
        categoryAdapter.submitList(list)
    }

    private fun loadCategoryFragment(name: String) {
        // Call the newInstance method to pass the category name
        val fragment = VideoListFragment.newInstance(name)

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun onBackPressed() {
        if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        } else {
            super.onBackPressed()
        }
    }

    // Inside MainActivity.kt
    fun openVideoPlayer(video: Video) {
        // Fill the sheet UI
        findViewById<TextView>(R.id.sheetVideoTitle).text = video.title
        findViewById<TextView>(R.id.sheetVideoDesc).text = "${video.channelName} • ${video.views} views"

        // Open the sheet
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}