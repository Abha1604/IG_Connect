package com.example.ig_connect

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ig_connect.Community

class Community_Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_community_, container, false)

        // Step 1: Find the RecyclerView in the layout
        val recyclerView: RecyclerView = view.findViewById(R.id.communityRecyclerView)

        // Step 2: Create a list of communities
        val communityList = listOf(
            Community("CGPA", "For academic help, study resources, peer advice."),
            Community("Hackathons", "Share upcoming hackathons, teams, ideas, and experiences."),
            Community("Internships/Placements", "Resume reviews, company insights, interview tips."),
            Community("General (Chill Zone)", "Memes, rants, or casual posts to keep it fun and lively.")
        )

        // Step 3: Set up the RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = CommunityAdapter(communityList) { community ->
            // Open the next activity or fragment
            val intent = Intent(requireContext(), CommunityPostsActivity::class.java)
            intent.putExtra("communityTitle", community.title) // Send community name to next screen
            startActivity(intent)
        }

        return view
    }
}
