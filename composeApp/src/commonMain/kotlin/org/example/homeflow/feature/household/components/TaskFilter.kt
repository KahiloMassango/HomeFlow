package org.example.homeflow.feature.household.components

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

@Composable
fun TaskFilter(
    modifier: Modifier = Modifier,
    filter: String,
    onFilterChange: (String) -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(
            count = "5",
            label = "All Tasks",
            selected = filter == "1",
            modifier = Modifier.weight(1f),
            onClick = { onFilterChange("1") })
        StatCard(
            count = "4",
            label = "To Do",
            selected = filter == "2",
            modifier = Modifier.weight(1f),
            onClick = { onFilterChange("2") })
        StatCard(
            count = "1",
            label = "In Progress",
            selected = filter == "3",
            modifier = Modifier.weight(1f),
            onClick = { onFilterChange("3") })
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
        elevation = CardDefaults.cardElevation(defaultElevation = if (selected) 0.dp else 2.dp),
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
