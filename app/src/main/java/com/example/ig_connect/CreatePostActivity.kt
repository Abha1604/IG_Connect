package com.example.ig_connect

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CreatePostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        val titleInput = findViewById<EditText>(R.id.postTitleInput)
        val contentInput = findViewById<EditText>(R.id.postContentInput)
        val submitButton = findViewById<Button>(R.id.submitPostButton)

        submitButton.setOnClickListener {
            val title = titleInput.text.toString()
            val content = contentInput.text.toString()

            if (title.isNotBlank() && content.isNotBlank()) {
                Toast.makeText(this, "Post submitted!", Toast.LENGTH_SHORT).show()
                finish() // Close the screen for now
            } else {
                Toast.makeText(this, "Please fill in both fields!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
