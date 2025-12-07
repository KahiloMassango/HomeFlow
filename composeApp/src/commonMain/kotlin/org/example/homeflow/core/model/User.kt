package org.example.homeflow.core.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String,
    val email: String,
    val phoneNumber: String,
    val profilePicUrl: String
)
