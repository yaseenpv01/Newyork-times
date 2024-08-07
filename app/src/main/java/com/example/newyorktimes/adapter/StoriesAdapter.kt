package com.example.newyorktimes.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newyorktimes.DetailActivity
import com.example.newyorktimes.R
import com.example.newyorktimes.model.Story

class StoriesAdapter : ListAdapter<Story, StoriesAdapter.StoryViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_story, parent, false)
        return StoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val story = getItem(position)
        holder.bind(story)
    }

    class StoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val bylineTextView: TextView = itemView.findViewById(R.id.bylineTextView)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(story: Story) {
            titleTextView.text = story.title
            bylineTextView.text = story.byline
            val multimedia = story.multimedia?.firstOrNull { it.format == "Large Thumbnail" } // Use the available format
            val imageUrl = multimedia?.url

            if (imageUrl != null) {
                Glide.with(itemView.context)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(imageView)
            } else {
                imageView.setImageResource(R.drawable.ic_launcher_foreground)
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("title", story.title)
                intent.putExtra("byline", story.byline)
                intent.putExtra("imageUrl", imageUrl)
                intent.putExtra("url", story.url)
                itemView.context.startActivity(intent)
            }


        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Story>() {
        override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem == newItem
        }
    }
}
