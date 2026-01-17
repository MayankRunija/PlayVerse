package com.example.videoplayer

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.videoplayer.model.Category
import com.example.videoplayer.model.Video

class MainActivity : AppCompatActivity() {

    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupCategories()

        // 1. Load the "All" fragment immediately so the app isn't empty on launch
        if (savedInstanceState == null) {
            loadCategoryFragment("All")
        }
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
        val fragment = VideoListFragment()
        // Optional: Pass the category name to the fragment via Arguments
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
    // Inside MainActivity.kt
    fun openVideoPlayer(video: Video) {
        // This is where we will eventually show the Bottom Sheet.
        // For now, let's just make sure it compiles.
        android.widget.Toast.makeText(this, "Clicked: ${video.title}", android.widget.Toast.LENGTH_SHORT).show()
    }
}