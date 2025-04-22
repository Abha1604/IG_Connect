package com.example.ig_connect.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ig_connect.R
import com.example.ig_connect.models_chat.User

class UsersAdapter(
    private val userList: List<User>,
    private val onUserClick: (User) -> Unit
) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userImage: ImageView = itemView.findViewById(R.id.imageProfile)
        val userName: TextView = itemView.findViewById(R.id.textViewName)
        val userEmail: TextView = itemView.findViewById(R.id.textViewEmail)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.userImage.setImageResource(user.profileImage)
        holder.userName.text = user.name
        holder.userEmail.text = user.email

        holder.itemView.setOnClickListener {
            onUserClick(user) // This triggers the click passed from ChatsFragment
        }
    }

    override fun getItemCount(): Int = userList.size
}
