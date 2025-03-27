package com.example.ig_connect

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//         Find the button by its ID
//        val clickButton: Button = findViewById(R.id.clickButton)
//        // Set an OnClickListener on the button
//        clickButton.setOnClickListener {
//            // Create an intent to open the HomePage activity
//            val intent = Intent(this@MainActivity, AfterLoginActivity::class.java)
//
//            // Start the new activity
//            startActivity(intent)
//        }


    }
    }
