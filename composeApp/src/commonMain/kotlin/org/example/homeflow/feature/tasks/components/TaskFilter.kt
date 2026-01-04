package org.example.homeflow.feature.tasks.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.homeflow.feature.tasks.model.TaskFilter

@Composable
fun TaskFilter(
    modifier: Modifier = Modifier,
    totalTasks: Int,
    todoTotalTasks: Int,
    doneTasks: Int,
    currentFilter: TaskFilter,
    onFilterChange: (TaskFilter) -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(
            count = totalTasks.toString(),
            label = "All Tasks",
            selected = TaskFilter.All == currentFilter,
            modifier = Modifier.weight(1f),
            onClick = { onFilterChange(TaskFilter.All) })
        StatCard(
            count = todoTotalTasks.toString(),
            label = "To Do",
            selected = TaskFilter.ToDo == currentFilter,
            modifier = Modifier.weight(1f),
            onClick = { onFilterChange(TaskFilter.ToDo) })
        StatCard(
            count = doneTasks.toString(),
            label = "Done",
            selected = TaskFilter.Done == currentFilter,
            modifier = Modifier.weight(1f),
            onClick = { onFilterChange(TaskFilter.Done) }
        )
    }
}

@Composable
private fun StatCard(
    count: String,
    label: String,
    selected: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.height(70.dp),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.secondary,
            contentColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSecondary
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = count,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSecondary.copy(
                    alpha = 0.5f
                )
            )
        }
    }
}
