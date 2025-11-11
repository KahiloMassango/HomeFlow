package org.example.homeflow.core.model

import androidx.compose.ui.graphics.Color

data class Task(
    val title: String,
    val assignedTo: String,
    val dueDate: String,
    val category: TaskCategory,
)

val tasks = listOf(
    Task("Buy milk and eggs", "Sarah", "Today", TaskCategory.Groceries),
    Task("Fix leaky faucet", "Mike", "Tomorrow", TaskCategory.Repairs),
    Task("Vacuum living room", "You", "Nov 5", TaskCategory.Cleaning),
    Task("Pay electricity bill", "Unsigned", "Dec 4", TaskCategory.Bills),
    Task("Clean kitchen counters", "Mike", "Today", TaskCategory.Cleaning)
)