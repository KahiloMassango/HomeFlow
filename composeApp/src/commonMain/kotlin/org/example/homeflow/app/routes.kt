package org.example.homeflow.app

import kotlinx.serialization.Serializable

@Serializable
object LoginRoute

@Serializable
object HomeRoute

@Serializable
data class AddTaskRoute(val houseId: String)

@Serializable
data class HouseRoute(val id: String)
