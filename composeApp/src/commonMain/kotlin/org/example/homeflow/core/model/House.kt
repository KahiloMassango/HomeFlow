package org.example.homeflow.core.model

import kotlinx.serialization.Serializable

@Serializable
data class House(
    val id: String,
    val code: String,
    val name: String,
    val members: Int,
)