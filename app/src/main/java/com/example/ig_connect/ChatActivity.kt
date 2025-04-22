package com.example.ig_connect

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ig_connect.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Receive the user data passed through the Intent
        val userName = intent.getStringExtra("userName")
        val userProfileImage = intent.getIntExtra("profileImage", R.drawable.ic_profile_placeholder)

        // Set the username and profile image
        val chatHeader: TextView = findViewById(R.id.chatHeader)
        val profileImage: ImageView = findViewById(R.id.profileImageChat)

        chatHeader.text = userName
        profileImage.setImageResource(userProfileImage)

        // You can implement the message input and send button functionality here
    }
}
