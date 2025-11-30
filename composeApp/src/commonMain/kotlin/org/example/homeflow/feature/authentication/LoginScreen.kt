package org.example.homeflow.feature.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    onLogin: () -> Unit,
) {
    Surface() {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginHeader()
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun LoginHeader() {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(100))
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(74.dp)
                    .padding(16.dp),
                imageVector = Icons.Outlined.Home,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        Text(
            text = "Home Flow",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Organize your home, together",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
    }
}