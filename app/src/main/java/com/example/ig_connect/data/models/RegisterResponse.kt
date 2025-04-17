package com.example.ig_connect.data.models

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)


data class RegisterResponse(
    val success: String,
    val user: User,
    val token: String
)

data class User(
    val name: String,
    val email: String,
    val password: String,
)
