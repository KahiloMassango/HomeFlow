package org.example.homeflow.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Membership(
    val id: String,
    val userId: String,
    val username: String,
    val houseId: String,
    val isOwner: Boolean,
    val joinedAt: Long
)
