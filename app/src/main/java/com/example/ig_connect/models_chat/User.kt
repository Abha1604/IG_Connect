package com.example.ig_connect.models_chat


import java.io.Serializable

data class User(
    val name: String? = null,
    val image: String? = null,
    val email: String? = null,
    val token: String? = null
) : Serializable
