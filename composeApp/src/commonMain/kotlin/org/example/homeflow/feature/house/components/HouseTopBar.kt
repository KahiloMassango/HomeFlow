package org.example.homeflow.feature.house.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.homeflow.core.ui.components.BackButton

@Composable
fun HouseTopBar(
    houseName: String,
    tasksToComplete: Int,
    oNavigateBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                BackButton(onClick = oNavigateBack)
                Text(houseName, color = MaterialTheme.colorScheme.onPrimary, fontSize = 20.sp)
            }
            Spacer(Modifier.height(18.dp))
            Text(
                text = "Tasks",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                "$tasksToComplete tasks to complete",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f)
            )
            Spacer(Modifier.height(12.dp))
        }
    }
}


