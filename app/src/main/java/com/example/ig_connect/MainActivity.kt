package com.example.ig_connect

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // View references
        val emailInput = findViewById<TextInputEditText>(R.id.emailInput)
        val passwordInput = findViewById<TextInputEditText>(R.id.passwordInput)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val signupLink = findViewById<TextView>(R.id.signupLink)

        // Login button click handler
        loginButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            val (isValid, errorMsg) = validateCredentials(email, password)

            if (isValid) {
                startActivity(Intent(this, AfterLoginActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, errorMsg ?: "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }


        // Signup link click handler
        signupLink.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    private fun validateCredentials(email: String, password: String): Pair<Boolean, String?> {
        if (email.isEmpty() || password.isEmpty()) {
            return Pair(false, "Please fill in all fields.")
        }

        if (!email.endsWith("@igdtuw.ac.in")) {
            return Pair(false, "Use valid college email")
        }

        if (password.length < 6) {
            return Pair(false, "Password must be at least 6 characters long.")
        }

        return Pair(true, null)
    }


}