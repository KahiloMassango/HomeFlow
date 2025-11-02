package org.example.homeflow.core.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HomeFlowButton(
    onClick: () -> Unit,
    color: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    content: @Composable (RowScope.() -> Unit)
) {
    Button(
        onClick = onClick,
        content = content,
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = contentColor
        )
    )
}

@Composable
fun HomeFlowOutlinedButton(
    onClick: () -> Unit,
    content: @Composable (RowScope.() -> Unit)
) {
    OutlinedButton(
        onClick = onClick,
        content = content,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.primary,
        )
    )
}