package org.example.homeflow.core.data.repositories

import kotlinx.coroutines.flow.Flow
import org.example.homeflow.core.model.Task
import org.example.homeflow.core.model.TaskCategory
import org.example.homeflow.core.model.TaskPriority

interface TaskRepository {
    fun getHouseTasks(houseId: String): Flow<List<Task>>
    fun deleteTask(id: String, houseId: String)
    suspend fun addTask(
        houseId: String,
        title: String,
        dueDate: Long,
        assignedTo: String?,
        description: String?,
        category: TaskCategory,
        priority: TaskPriority,
    )

    suspend fun updateTask(
        taskId: String,
        houseId: String,
        title: String,
        dueDate: Long,
        assignedTo: String?,
        description: String?,
        category: TaskCategory,
        priority: TaskPriority
    )

}