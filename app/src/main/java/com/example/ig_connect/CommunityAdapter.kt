package com.example.ig_connect

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CommunityAdapter(
    private val communities: List<Community>,
    private val onClick: (Community) -> Unit
) : RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder>() {

    inner class CommunityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.communityTitle)
        val desc: TextView = itemView.findViewById(R.id.communityDescription)
        val icon: ImageView = itemView.findViewById(R.id.communityIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_community, parent, false)
        return CommunityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        val community = communities[position]
        holder.title.text = community.title
        holder.desc.text = community.description
        holder.icon.setImageResource(community.iconResId)
        holder.itemView.setOnClickListener { onClick(community) }
    }

    override fun getItemCount(): Int = communities.size
}
