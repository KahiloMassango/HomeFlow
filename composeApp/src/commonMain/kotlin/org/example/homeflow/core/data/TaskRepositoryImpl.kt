package org.example.homeflow.core.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import org.example.homeflow.core.data.repositories.TaskRepository
import org.example.homeflow.core.model.Task
import org.example.homeflow.core.model.TaskCategory
import org.example.homeflow.core.model.TaskPriority
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class TaskRepositoryImpl : TaskRepository {

    private val tasks = MutableStateFlow<List<Task>>(mockTasks)

    override fun getHouseTasks(houseId: String): Flow<List<Task>> =
        tasks.map { list -> list.filter { it.houseId == houseId } }

    override fun deleteTask(id: String, houseId: String) {
        // Remove only if it belongs to that house
        tasks.value = tasks.value.filterNot { it.id == id && it.houseId == houseId }
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
    ) {
        val newTask = Task(
            id = Uuid.random().toString(),
            title = title,
            assignedTo = assignedTo,   // Fill as needed
            dueDate = dueDate,      // Fill as needed
            description = description,
            category = category,
            priority = priority,
            houseId = houseId,
            done = false
        )
        tasks.value += newTask
    }

    override suspend fun updateTask(
        taskId: String,
        houseId: String,
        title: String,
        dueDate: Long,
        assignedTo: String?,
        description: String?,
        category: TaskCategory,
        priority: TaskPriority
    ) {
        tasks.value = tasks.value.map { task ->
            if (task.houseId == houseId && task.id == taskId) {
                task.copy(
                    title = title,
                    assignedTo = assignedTo,   // Fill as needed
                    dueDate = dueDate,      // Fill as needed
                    description = description,
                    category = category,
                    priority = priority,
                )
            } else task
        }
    }
}

private val mockTasks = listOf(Task(
    id = "eggs",
    title = "Buy milk and eggs",
    assignedTo = "Sarah",
    dueDate = 1763806098939,
    description = "Pick up groceries at the store.",
    category = TaskCategory.Groceries,
    priority = TaskPriority.High,
    houseId = "fsdf",
    done = false
),
    Task(
        id = "faucet",
        title = "Fix leaky faucet",
        assignedTo = "Mike",
        dueDate = 1763806088939,
        description = "Kitchen faucet is dripping; tools in the garage.",
        category = TaskCategory.Repairs,
        priority = TaskPriority.Medium,
        houseId = "fsdf",
        done = false
    ),
    Task(
        id = "room",
        title = "Vacuum living room",
        assignedTo = "You",
        dueDate = 1763806068939,
        description = "Make sure to get under the sofa.",
        category = TaskCategory.Cleaning,
        priority = TaskPriority.Low,
        houseId = "fsdf",
        done = true
    ),
    Task(
        id = "bill",
        title = "Pay electricity bill",
        assignedTo = "Unsigned",
        dueDate =1763806048939,
        description = "Use the payment portal or banking app.",
        category = TaskCategory.Bills,
        priority = TaskPriority.High,
        houseId = "sfdsdf",
        done = true
    ),
    Task(
        id = "counters",
        title = "Clean kitchen counters",
        assignedTo = "Mike",
        dueDate = 1763806028939,
        description = "Use disinfectant spray—found under sink.",
        category = TaskCategory.Cleaning,
        priority = TaskPriority.Medium,
        houseId = "sfdsdf",
        done = false
    ))
