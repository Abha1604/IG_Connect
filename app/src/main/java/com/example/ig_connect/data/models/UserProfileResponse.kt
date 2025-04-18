package com.example.ig_connect.data.models

data class UserProfileResponse(
    val id: Int,
    val name: String,
    val email: String
)

data class UserProfile(
    val name: String,
    val email: String,
    val bio: String,
    val skills: String,
    val projectTitle: String,
    val projectDescription: String,
    val projectTech: String,
    val githubProjectLink: String,
    val githubLink: String,
    val linkedinLink: String,
    val profileImageUrl: String // if using Glide or Picasso
)

