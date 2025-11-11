package org.example.homeflow.app

import kotlinx.serialization.Serializable

@Serializable
object LoginRoute

@Serializable
object HomeRoute

@Serializable
data class AddTaskRoute(val householdId: String)

@Serializable
data class HouseholdDetailRoute(val id: String)
