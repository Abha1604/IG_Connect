package com.example.ig_connect

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PostAdapter(private val postList: List<Post>) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.postTitleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.postContentTextView)
        val authorTextView: TextView = itemView.findViewById(R.id.postAuthorTextView)
        val timeTextView: TextView = itemView.findViewById(R.id.postTimeTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postList[position]
        holder.titleTextView.text = post.title
        holder.contentTextView.text = post.content
        holder.authorTextView.text = "by ${post.author}"
        holder.timeTextView.text = post.timestamp
    }

    override fun getItemCount(): Int = postList.size
}
