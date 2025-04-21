package com.example.ig_connect

import androidx.appcompat.app.AppCompatDelegate
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ig_connect.data.models.LoginRequest
import com.example.ig_connect.data.models.LoginResponse
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        // Check token in SharedPreferences
        val token = getSharedPreferences("IGConnectPrefs", Context.MODE_PRIVATE)
            .getString("auth_token", null)

        if (token != null) {
            startActivity(Intent(this, AfterLoginActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_main)

        val emailInput = findViewById<TextInputEditText>(R.id.emailInput)
        val passwordInput = findViewById<TextInputEditText>(R.id.passwordInput)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val signupLink = findViewById<TextView>(R.id.signupLink)

        loginButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            val (isValid, errorMsg) = validateCredentials(email, password)

            if (isValid) {
                performLogin(email, password)
            } else {
                Toast.makeText(this, errorMsg ?: "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }

        signupLink.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    private fun validateCredentials(email: String, password: String): Pair<Boolean, String?> {
        if (email.isEmpty() || password.isEmpty()) return Pair(false, "Please fill in all fields.")
        if (!email.endsWith("@igdtuw.ac.in")) return Pair(false, "Use valid college email")
        if (password.length < 6) return Pair(false, "Password must be at least 6 characters long.")
        return Pair(true, null)
    }

    private fun performLogin(email: String, password: String) {
        val request = LoginRequest(email, password)
        RetrofitClient.instance.loginUser(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val token = response.body()!!.token
                    val sharedPref = getSharedPreferences("IGConnectPrefs", Context.MODE_PRIVATE)
                    sharedPref.edit().putString("auth_token", token).apply()

                    startActivity(Intent(this@MainActivity, AfterLoginActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@MainActivity, "Login failed: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Login error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
