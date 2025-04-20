package com.example.ig_connect

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ReplyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reply)

        val postId = intent.getIntExtra("postId", -1)
        val postContent = intent.getStringExtra("postContent") ?: "No content available"

        val replyingToText = findViewById<TextView>(R.id.replyingToText)
        val replyEditText = findViewById<EditText>(R.id.replyEditText)
        val submitButton = findViewById<Button>(R.id.submitReplyButton)

        replyingToText.text = "Replying to: \"$postContent\""

        submitButton.setOnClickListener {
            val replyText = replyEditText.text.toString()
            if (replyText.isNotBlank()) {
                // Later: send to backend or local storage
                Toast.makeText(this, "✅ Reply posted successfully!", Toast.LENGTH_SHORT).show()
                finish() // close activity
            } else {
                Toast.makeText(this, "⚠️ Reply cannot be empty.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
