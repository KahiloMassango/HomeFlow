package org.example.homeflow.feature.add_task.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.homeflow.core.model.TaskCategory
import org.example.homeflow.core.model.TaskPriority
import org.example.homeflow.core.ui.components.AppDropdown

@Composable
fun CategoryAndPriorityContainer(
    category: TaskCategory,
    priority: TaskPriority,
    onCategoryUpdate: (TaskCategory) -> Unit,
    onPriorityUpdate: (TaskPriority) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AppDropdown(
            modifier = Modifier.weight(1f),
            items = TaskCategory.entries.map { it.title },
            selected = category.title,
            onSelected = {
                onCategoryUpdate(TaskCategory.entries.find { category -> category.title == it }!!)
            },
            label = "Category",
            borderColor = category.color,
            leadingIcon = {
                Icon(
                    imageVector = category.icon,
                    contentDescription = null
                )
            },
        )
        AppDropdown(
            modifier = Modifier.weight(1f),
            items = TaskPriority.entries.map { it.title },
            selected = priority.title,
            onSelected = {
                onPriorityUpdate(TaskPriority.entries.find { priority -> priority.title == it }!!)
            },
            label = "Priority",
            borderColor = priority.color,
            leadingIcon = {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(priority.color, CircleShape)
                )

            }
        )
    }
}

