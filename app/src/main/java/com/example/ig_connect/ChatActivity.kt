package com.example.ig_connect

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ig_connect.adapters.MessagesAdapter
import com.example.ig_connect.databinding.ActivityChatBinding
import com.example.ig_connect.models_chat.Message

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var messagesAdapter: MessagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userName = intent.getStringExtra("userName") ?: "Unknown"

        binding.textViewUserName.text = userName

        // Dummy messages
        val dummyMessages = listOf(
            Message(userName, "Hey!"),
            Message("You", "Hello $userName!"),
            Message(userName, "How are you?"),
            Message("You", "I'm good, what about you?"),
            Message(userName, "Doing great, thanks!")
        )

        messagesAdapter = MessagesAdapter(dummyMessages)

        binding.recyclerViewMessages.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity)
            adapter = messagesAdapter
        }
    }
}
