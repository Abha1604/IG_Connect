package com.example.ig_connect.data.models

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val success: String
)
