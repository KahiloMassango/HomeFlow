package org.example.homeflow.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeTopBar(
    onLogout: () -> Unit,
) {
// Top Header
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

            Spacer(Modifier.width(14.dp))
           Row(
               modifier = Modifier.fillMaxWidth(),
               verticalAlignment = Alignment.CenterVertically,
               horizontalArrangement = Arrangement.SpaceBetween,
           ) {
               Text("Welcome back", color = MaterialTheme.colorScheme.onPrimary, fontSize = 16.sp)
               IconButton(onClick = { onLogout() }) {
                   Icon(
                       imageVector = Icons.AutoMirrored.Filled.Logout,
                       contentDescription = null,
                       tint = MaterialTheme.colorScheme.onPrimary,
                   )
               }
           }

            Spacer(Modifier.height(18.dp))
            Text(
                text = "Your Houses",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}