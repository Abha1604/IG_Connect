package com.example.ig_connect

import Message
import MessagesAdapter
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.ig_connect.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var messagesAdapter: MessagesAdapter // Fixed the typo
    private val messages = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Receive the user data passed through the Intent
        val userName = intent.getStringExtra("userName")
        val userProfileImage = intent.getIntExtra("profileImage", R.drawable.ic_profile_placeholder)

        // Set the username and profile image
        binding.chatHeader.text = userName
        binding.profileImageChat.setImageResource(userProfileImage)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true  //
        binding.recyclerViewMessages.layoutManager = layoutManager
        // Set up RecyclerView for chat messages
        messagesAdapter = MessagesAdapter(messages) // Use the declared property
        binding.recyclerViewMessages.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewMessages.adapter = messagesAdapter

        // Handle sending message
        binding.sendButton.setOnClickListener {
            val messageContent = binding.messageInput.text.toString()

            if (messageContent.isNotBlank()) {
                // Create a new message with sender info (use dynamic data here)
                val newMessage = Message(
                    content = messageContent,
                    sender = userName ?: "Unknown",  // Use received userName
                    profileImageUrl = "https://example.com/user1.jpg",  // Placeholder URL, replace as necessary
                    isSent = true  // Set to true for sent messages
                )

                // Add the message to the list and update the RecyclerView
                messages.add(newMessage)
                messagesAdapter.notifyItemInserted(messages.size - 1)

                // Scroll to the latest message
                binding.recyclerViewMessages.scrollToPosition(messages.size - 1)

                // Clear the input field
                binding.messageInput.text.clear()
            }
        }
    }
}
