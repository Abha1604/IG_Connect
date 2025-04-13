package com.example.ig_connect
import android.util.Log
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class SignupActivity : AppCompatActivity() {

    private lateinit var nameInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var confirmPasswordInput: TextInputEditText
    private lateinit var signupButton: Button
    private lateinit var loginLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        initializeViews()
        setupClickListeners()
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
        signupButton.setOnClickListener { attemptSignup() }
        loginLink.setOnClickListener { navigateToLogin() }
    }

    private fun attemptSignup() {
        val name = nameInput.text.toString().trim()
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()
        val confirmPassword = confirmPasswordInput.text.toString().trim()

        when {
            name.isEmpty() -> showError("Please enter your full name", nameInput)
            email.isEmpty() -> showError("Please enter your email", emailInput)
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> showError("Invalid email format", emailInput)
            password.isEmpty() -> showError("Please enter a password", passwordInput)
            password.length < 6 -> showError("Password must be at least 6 characters", passwordInput)
            password != confirmPassword -> showError("Passwords don't match", confirmPasswordInput)
            else -> proceedToProfileDetails(name, email)
        }
    }

    private fun proceedToProfileDetails(name: String, email: String) {
        val intent = Intent(this, ProfileDetailsActivity::class.java).apply {
            putExtra("USER_NAME", name)
            putExtra("USER_EMAIL", email)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()

        // Add log to verify this code is reached
        Log.d("SignupFlow", "Navigating to ProfileDetails")
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showError(message: String, field: TextInputEditText) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        field.requestFocus()
    }
}