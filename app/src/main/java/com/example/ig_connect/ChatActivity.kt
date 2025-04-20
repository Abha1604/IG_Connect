package com.example.ig_connect

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ig_connect.adapters.MessagesAdapter
import com.example.ig_connect.models_chat.Message

class ChatActivity : AppCompatActivity() {

    private lateinit var toolbarName: TextView
    private lateinit var toolbarImage: ImageView
    private lateinit var recyclerViewMessages: RecyclerView
    private lateinit var editTextMessage: EditText
    private lateinit var buttonSend: Button

    private val messagesList = mutableListOf<Message>()
    private lateinit var messagesAdapter: MessagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // Initialize toolbar views
        toolbarName = findViewById(R.id.toolbarName)
        toolbarImage = findViewById(R.id.toolbarProfileImage)

        // Set user name and profile image
        toolbarName.text = "Aayush"  // You can set this dynamically
        toolbarImage.setImageResource(R.drawable.ic_profile_placeholder) // Set your placeholder vector drawable here

        // Initialize message area
        recyclerViewMessages = findViewById(R.id.recyclerViewMessages)
        editTextMessage = findViewById(R.id.editTextMessage)
        buttonSend = findViewById(R.id.buttonSend)

        // Setup RecyclerView
        messagesAdapter = MessagesAdapter(messagesList)
        recyclerViewMessages.layoutManager = LinearLayoutManager(this)
        recyclerViewMessages.adapter = messagesAdapter

        // Handle send button click
        buttonSend.setOnClickListener {
            val messageText = editTextMessage.text.toString().trim()
            if (messageText.isNotEmpty()) {
                val message = Message(
                    text = messageText,
                    isSentByMe = true
                )
                messagesList.add(message)
                messagesAdapter.notifyItemInserted(messagesList.size - 1)
                recyclerViewMessages.scrollToPosition(messagesList.size - 1)
                editTextMessage.text.clear()
            }
        }
    }
}
