package org.example.homeflow.feature.tasks.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.homeflow.core.ui.components.BackButton

@Composable
fun TasksTopBar(
    houseName: String,
    houseCode: String,
    isHouseOwner: Boolean,
    onDelete: () -> Unit,
    oNavigateBack: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                BackButton(onClick = oNavigateBack)
                Text(houseName, color = MaterialTheme.colorScheme.onPrimary, fontSize = 20.sp)
                Spacer(modifier = Modifier.weight(1f))
                if (isHouseOwner) {
                    IconButton(onClick = { onDelete() }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
            if (isHouseOwner) {
                Spacer(Modifier.height(18.dp))
                HouseCodeContainer(houseCode)
            }
            Spacer(Modifier.height(18.dp))
            Text(
                text = "Tasks",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun HouseCodeContainer(houseCode: String) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.shapes.medium)
            .clip(MaterialTheme.shapes.medium)
    ) {
        Row(
            modifier = Modifier.padding(6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = houseCode,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontSize = 16.sp,
            )
            Text(
                text = "Copy",
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.5f),
                fontSize = 12.sp,
            )

        }
    }
}

