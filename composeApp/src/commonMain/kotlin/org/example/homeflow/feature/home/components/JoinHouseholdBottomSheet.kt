package org.example.homeflow.feature.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.homeflow.core.ui.components.HomeFlowButton


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinHouseholdBottomSheet(
    onDismiss: () -> Unit,
    onJoin: (String) -> Unit
) {
    var code by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp),
        contentColor = MaterialTheme.colorScheme.onSurface,
        containerColor = MaterialTheme.colorScheme.surface,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            // Header Row
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Join Household",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, null)
                }
            }

            Spacer(Modifier.height(20.dp))

            Text("Household Name", fontWeight = FontWeight.Medium, fontSize = 14.sp)
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = code,
                onValueChange = { code = it },
                placeholder = { Text("ENTER-CODE-HERE") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.height(20.dp))

            InfoBox(
                modifier = Modifier,
                icon = Icons.Outlined.Info,
                title = "Private & Secure",
                description = "Ask a household member for the invite code"
            )

            Spacer(Modifier.height(30.dp))

            HomeFlowButton(
                modifier = Modifier.fillMaxWidth(),
                enabled = code.isNotBlank(),
                onClick = { onJoin(code) },
            ) {
                Text("Create Household")
            }

        }
    }
}

