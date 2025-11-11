package org.example.homeflow.core.model

import androidx.compose.ui.graphics.Color

enum class TaskPriority(val title: String, val color: Color) {
    Low("Low",  Color(0xFF10b981)),
    Medium("Medium", Color(0xFFf59e0b)),
    High("High",  Color(0xFFef4444)),
}