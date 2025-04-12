package com.example.ig_connect.models

data class ProfileDetailsRequest(
    val userId: String,
    val photoURL: String,
    val skills: List<String>,
    val bio: String,
    val department: String,
    val year: Int,
    val cgpa: String,
    val linkedin: String,
    val isMentor: Boolean,
    val experience: List<Experience> = emptyList()
) {
    data class Experience(
        val position: String,
        val company: String,
        val startDate: String,
        val endDate: String?,
        val description: String,
        val currentlyWorking: Boolean
    )
}