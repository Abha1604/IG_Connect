package com.example.ig_connect.data.models

data class ProfileDetailsRequest(
    val Year: Int,
    val CGPA: String,
    val Bio: String,
    val Skills: List<String>,
    val Linkedin: String,
    val Experience: List<UserExperience>,
    val Internships: List<Any> = emptyList(),
    val ScholarshipsAcquired: List<Any> = emptyList(),
    val Freelancing: List<Any> = emptyList()
)

data class UserExperience(
    val position: String,
    val company: String,
    val startDate: String,
    val endDate: String?,
    val description: String,
    val currentlyWorking: Boolean
)




data class ProfileDetailsResponse(
    val success: String?,
    val updatedUser: UpdatedUser?,
    val error: String?
)

data class UpdatedUser(
    val id: String,
    val name: String,
    val email: String,
    val details: UserDetails
)

data class UserDetails(
    val userID: String,
    val Bio: String,
    val Year: Int,
    val CGPA: String,
    val Skills: List<String>,
    val Linkedin: String,
    val photoURL: String?,
    val Experience: List<Experience>?,
    val Internships: List<String>?,
    val Freelancing: List<String>?,
    val scholarshipAcquired: List<String>?,
    val Mentor: Mentor?
)

data class Experience(
    val position: String,
    val company: String,
    val startDate: String,
    val endDate: String?,
    val description: String,
    val currentlyWorking: Boolean
)

data class Mentor(
    val internship: Boolean,
    val placements: Boolean,
    val freelancing: Boolean,
    val scholarship: Boolean,
    val cgpa: Boolean
)
