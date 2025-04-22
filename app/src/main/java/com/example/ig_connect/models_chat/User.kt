package com.example.ig_connect.models_chat

import com.example.ig_connect.R

data class User(
    var id: String = "",
    var name: String = "",
    var email: String = "",
    var profileImage: Int = R.drawable.ic_profile_placeholder
)