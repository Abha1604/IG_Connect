data class Message(
    val content: String,
    val sender: String,
    val profileImageUrl: String, // URL for the profile image
    val isSent: Boolean // This helps to align messages
)
