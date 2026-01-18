package com.example.videoplayer.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.videoplayer.R
import com.example.videoplayer.model.Video

class VideoAdapter(private val onVideoClick: (Video) -> Unit) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    private var videos = listOf<Video>()

    fun submitList(list: List<Video>) {
        videos = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video_card, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videos[position]
        Log.d("VideoApp", "Binding video at position: $position")
        holder.title.text = video.title
        holder.meta.text = "${video.channelName} • ${video.views} views • ${video.timeAgo}"

        // In a real app, use Glide or Coil to load images
        // holder.thumbnail.setImageResource(video.thumbnailResId)

        holder.itemView.setOnClickListener { onVideoClick(video) }
    }

    override fun getItemCount() = videos.size

    class VideoViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val thumbnail: ImageView = v.findViewById(R.id.videoThumbnail)
        val title: TextView = v.findViewById(R.id.videoTitle)
        val meta: TextView = v.findViewById(R.id.videoMeta)
    }
}