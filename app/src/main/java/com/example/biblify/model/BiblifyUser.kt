package com.example.biblify.model

data class BiblifyUser (
    val id: String?,
    val userId: String,
    val displayName: String,
    val avatarUrl: String,
    val quote: String,
    val profession: String
) {
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "quote" to this.quote,
            "user_id" to this.userId,
            "display_name" to this.displayName,
            "avatar_url" to this.avatarUrl,
            "profession" to this.profession,
        )
    }
}
