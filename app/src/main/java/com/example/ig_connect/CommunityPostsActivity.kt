package com.example.ig_connect

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CommunityPostsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_posts)

        val title = intent.getStringExtra("communityTitle")?.trim()?.replaceFirstChar { it.uppercase() } ?: "Unknown"
        Log.d("CommunityPostsActivity", "Received title from intent: '$title'")

        val headingTextView = findViewById<TextView>(R.id.headingTextView)
        val postsRecyclerView = findViewById<RecyclerView>(R.id.postsRecyclerView)

        "$title Posts".also { headingTextView.text = it }

        // Dummy posts based on selected community
        val posts = when (title) {
            "CGPA" -> listOf(
                Post("How to calculate SGPA?", "Can someone explain with an example?", "Aakanksha", "2 hrs ago"),
                Post("Any tips for 1st sem scoring?", "What subjects to focus on?", "Abha", "1 day ago")
            )
            "Hackathons" -> listOf(
                Post("Looking for team for SIH", "DM me if interested in blockchain track", "Akshita", "3 hrs ago"),
                Post("How to build MVP in 24 hrs?", "Share some quick frameworks", "Anamika", "1 day ago")
            )
            "Internships/Placements" -> listOf(
                Post("Resume format for 2nd year?", "1-pager or 2-pager?", "Anuja", "4 hrs ago"),
                Post("Good startups for internship?", "Looking for remote roles", "Avika", "Yesterday")
            )
            "General (Chill Zone)" -> listOf(
                Post("Fun memes only", "Here’s one I found today 😂", "Ayushi", "Just now"),
                Post("What’s your fav coding playlist?", "Lo-fi or trance?", "Dimpal", "2 hrs ago")
            )
            else -> listOf(
                Post("No posts available", "", "System", "Now")
            )
        }


        postsRecyclerView.layoutManager = LinearLayoutManager(this)
        postsRecyclerView.adapter = PostAdapter(posts)
    }
}
