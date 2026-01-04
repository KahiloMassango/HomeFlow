package org.example.homeflow.core.data

import co.touchlab.kermit.Logger
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.homeflow.core.data.repositories.TaskRepository
import org.example.homeflow.core.data.util.Result
import org.example.homeflow.core.model.Membership
import org.example.homeflow.core.model.Task
import org.example.homeflow.core.model.TaskCategory
import org.example.homeflow.core.model.TaskPriority
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class TaskRepositoryImpl : TaskRepository {

    private val firestore = Firebase.firestore

    override suspend fun getTaskById(taskId: String): Result<Task> {
        try {
            val snapshot = firestore.collection("tasks")
                .where { "id".equalTo(taskId) }
                .get()

            return Result.Success(snapshot.documents.first().data<Task>())
        } catch (e: Exception) {
            return Result.Error()
        }
    }

    override fun getHouseTasksFlow(houseId: String): Flow<List<Task>> =
        firestore.collection("tasks")
            .where { "houseId".equalTo(houseId) }
            .snapshots
            .map { snapshot ->
                snapshot.documents.map { it.data<Task>() }
            }

    override suspend fun deleteTask(id: String, houseId: String): Result<Unit> {
        try {
            firestore.collection("tasks")
                .document(id)
                .delete()
            return Result.Success(Unit)
        } catch (e: Exception) {
            return Result.Error()
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun addTask(
        houseId: String,
        title: String,
        dueDate: Long,
        assignedTo: String?,
        description: String?,
        category: TaskCategory,
        priority: TaskPriority
    ): Result<Unit> {
        try {
            val id = Uuid.random().toString()
            val newTask = Task(
                id = id,
                title = title,
                assignedTo = assignedTo,
                dueDate = dueDate,
                description = description,
                category = category,
                priority = priority,
                houseId = houseId,
                done = false
            )
            firestore.collection("tasks")
                .document(id)
                .set(newTask)
            return Result.Success(Unit)
        } catch (e: Exception) {
            return Result.Error()
        }
    }

    override suspend fun updateTask(
        taskId: String,
        houseId: String,
        title: String,
        dueDate: Long?,
        assignedTo: String?,
        description: String?,
        category: TaskCategory,
        priority: TaskPriority
    ): Result<Unit> {
        try {
            val updatedTask = Task(
                id = taskId,
                title = title,
                dueDate = dueDate,
                assignedTo = assignedTo,
                description = description,
                category = category,
                priority = priority,
                houseId = houseId,
                done = false,
            )


            firestore.collection("tasks")
                .document(taskId)
                .update(updatedTask)
            return Result.Success(Unit)
        } catch (e: Exception) {
            return Result.Error()
        }
    }
}
