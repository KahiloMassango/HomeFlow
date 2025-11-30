package org.example.homeflow.core.model

import kotlinx.datetime.LocalDate

data class Task(
    val id: String,
    val title: String,
    val assignedTo: String?,
    val dueDate: Long?,
    val description: String?,
    val category: TaskCategory,
    val priority: TaskPriority,
    val houseId: String,
    val done: Boolean,
)