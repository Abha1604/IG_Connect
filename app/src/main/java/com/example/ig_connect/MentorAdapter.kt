package com.example.ig_connect

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.util.Log


class MentorAdapter(private val mentors: List<Mentor>) : RecyclerView.Adapter<MentorAdapter.MentorViewHolder>() {

    inner class MentorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage: ImageView = itemView.findViewById(R.id.mentorProfileImage)
        val name: TextView = itemView.findViewById(R.id.mentorName)
        val details: TextView = itemView.findViewById(R.id.mentorDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MentorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mentor, parent, false)
        return MentorViewHolder(view)
    }

    override fun onBindViewHolder(holder: MentorViewHolder, position: Int) {
        val mentor = mentors[position]
        holder.profileImage.setImageResource(mentor.profileImageResId)
        holder.name.text = mentor.name
        @SuppressLint("SetTextI18n")
        holder.details.text = "${mentor.year}, ${mentor.branch}"
        Log.d("MentorAdapter", "Binding mentor: ${mentor.name}, Year: ${mentor.year}, Branch: ${mentor.branch}")

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("userName", mentor.name)
            intent.putExtra("profileImage", mentor.profileImageResId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = mentors.size
}
