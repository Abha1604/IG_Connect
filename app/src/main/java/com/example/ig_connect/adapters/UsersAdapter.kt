package com.example.ig_connect.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ig_connect.models_chat.User
import com.example.ig_connect.databinding.ItemContainerUserBinding

class UsersAdapter(private val users: List<User>) :
    RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemContainerUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.setUserData(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }

    inner class UserViewHolder(private val binding: ItemContainerUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setUserData(user: User) {
            binding.textName.text = user.name
            binding.textEmail.text = user.email
            binding.imageProfile.setImageBitmap(getUserImage(user.image))
        }

        private fun getUserImage(encodedImage: String?): Bitmap? {
            if (encodedImage == null) return null
            val bytes = Base64.decode(encodedImage, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }
    }
}
