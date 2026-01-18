package com.example.videoplayer.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.videoplayer.R
import com.example.videoplayer.model.Category
import com.google.android.material.card.MaterialCardView

class CategoryAdapter(private val onCategoryClick: (Category) -> Unit) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var categories = listOf<Category>()
    private var selectedPosition = 0

    fun submitList(list: List<Category>) {
        categories = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.name.text = category.name

        // Theme colors
        val goldColor = Color.parseColor("#F2C970")
        val midnightSurface = Color.parseColor("#1A1A1A")

        if (position == selectedPosition) {
            // SELECTED: Show your Gold Gradient View + White Text
            holder.selectedBg.visibility = View.VISIBLE
            holder.name.setTextColor(Color.WHITE)
            holder.card.strokeWidth = 0
            // Optional: make card transparent to let the gradient show through
            holder.card.setCardBackgroundColor(Color.TRANSPARENT)
        } else {
            // UNSELECTED: Midnight Background + Gold Text
            holder.selectedBg.visibility = View.GONE
            holder.card.setCardBackgroundColor(midnightSurface)
            holder.name.setTextColor(goldColor)
            holder.card.strokeWidth = 2 // Subtle gold border
            holder.card.setStrokeColor(android.content.res.ColorStateList.valueOf(Color.parseColor("#33F2C970")))
        }

        holder.itemView.setOnClickListener {
            if (selectedPosition != holder.adapterPosition) {
                val oldPos = selectedPosition
                selectedPosition = holder.adapterPosition
                notifyItemChanged(oldPos)
                notifyItemChanged(selectedPosition)
                onCategoryClick(category)
            }
        }
    }

    override fun getItemCount() = categories.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.categoryName)
        val card: MaterialCardView = view.findViewById(R.id.categoryCard)
        // Add the reference to the gradient view we added in the XML
        val selectedBg: View = view.findViewById(R.id.selectedGradientBg)
    }
}