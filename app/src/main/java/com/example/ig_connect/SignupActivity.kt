package com.example.ig_connect

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class SignupActivity : AppCompatActivity() {

    // Declare views as lateinit to avoid null checks
    private lateinit var nameInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var confirmPasswordInput: TextInputEditText
    private lateinit var signupButton: Button
    private lateinit var loginLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        try {
            // Initialize views safely
            initializeViews()

            // Set up click listeners
            setupClickListeners()

            // Log successful initialization
            Log.d("SignupActivity", "Activity created successfully")
        } catch (e: Exception) {
            Log.e("SignupActivity", "Initialization failed", e)
            Toast.makeText(this, "Error initializing screen", Toast.LENGTH_SHORT).show()
            finish() // Close activity if setup fails
        }
    }

    private fun initializeViews() {
        nameInput = findViewById(R.id.nameInput)
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput)
        signupButton = findViewById(R.id.signupButton)
        loginLink = findViewById(R.id.loginLink)
    }

    private fun setupClickListeners() {
        // Signup Button Click
        signupButton.setOnClickListener {
            try {
                attemptSignup()
            } catch (e: Exception) {
                Log.e("SignupActivity", "Signup failed", e)
                Toast.makeText(this, "Signup process error", Toast.LENGTH_SHORT).show()
            }
        }

        // Login Link Click
        loginLink.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun attemptSignup() {
        val name = nameInput.text.toString().trim()
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()
        val confirmPassword = confirmPasswordInput.text.toString().trim()

        when {
            name.isEmpty() -> showError("Please enter your full name")
            email.isEmpty() -> showError("Please enter your email")
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> showError("Invalid email format")
            password.isEmpty() -> showError("Please enter a password")
            password.length < 6 -> showError("Password must be at least 6 characters")
            password != confirmPassword -> showError("Passwords don't match")
            else -> processSignup(name, email, password)
        }
    }

    private fun processSignup(name: String, email: String, password: String) {
        // Show loading state (you can add a progress bar later)
        signupButton.isEnabled = false

        // TODO: Replace with your actual signup logic (Firebase, API call, etc.)
        simulateSignupProcess(name, email, password)
    }

    private fun simulateSignupProcess(name: String, email: String, password: String) {
        // Simulate network delay
        android.os.Handler().postDelayed({
            runOnUiThread {
                signupButton.isEnabled = true

                // For now, just show success and go to login
                Toast.makeText(this, "Signup successful!", Toast.LENGTH_SHORT).show()
                navigateToLogin()

                // In real app, you would:
                // 1. Create user in database
                // 2. Send verification email
                // 3. Proceed to main app or verification screen
            }
        }, 1500)
    }

    private fun navigateToLogin() {
        try {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish() // Close signup activity
        } catch (e: Exception) {
            Log.e("SignupActivity", "Navigation failed", e)
            Toast.makeText(this, "Cannot open login screen", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        // You can add specific error highlighting here
        when (message) {
            "Please enter your full name" -> nameInput.requestFocus()
            "Invalid email format" -> emailInput.requestFocus()
            "Passwords don't match" -> confirmPasswordInput.requestFocus()
        }
    }

    // Handle configuration changes safely
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("name", nameInput.text.toString())
        outState.putString("email", emailInput.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        nameInput.setText(savedInstanceState.getString("name", ""))
        emailInput.setText(savedInstanceState.getString("email", ""))
    }
}