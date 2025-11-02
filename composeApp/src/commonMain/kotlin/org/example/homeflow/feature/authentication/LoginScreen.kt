package org.example.homeflow.feature.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen() {
    Surface() {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginHeader()
        }
    }
}

@Composable
fun LoginHeader() {
    Column(
        modifier = Modifier
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(24.dp).padding(16.dp),
                imageVector = Icons.Filled.Home,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        Text("Home FLow", style = MaterialTheme.typography.headlineSmall)
        Text("Organize your home, together", style = MaterialTheme.typography.bodyMedium)
    }
}