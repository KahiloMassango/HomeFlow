package org.example.homeflow.core.model

import androidx.compose.ui.graphics.Color

data class Task(
    val id: String,
    val title: String,
    val assignedTo: String?,
    val dueDate: String?,
    val description: String?,
    val category: TaskCategory,
    val priority: TaskPriority,
    val houseId: String,
)