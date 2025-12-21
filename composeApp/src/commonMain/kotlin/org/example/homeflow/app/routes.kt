package org.example.homeflow.app

import kotlinx.serialization.Serializable

@Serializable
object LoginRoute

@Serializable
object HomeRoute

@Serializable
data class AddTaskRoute(val houseId: String)

@Serializable
data class EditTaskRoute(val houseId: String, val taskId: String)

@Serializable
data class TasksRoute(val id: String)
