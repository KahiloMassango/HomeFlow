package org.example.homeflow.core.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.HomeRepairService
import androidx.compose.material.icons.filled.OtherHouses
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class TaskCategory(val title: String, val icon: ImageVector, val color: Color){
    Groceries("Groceries", Icons.Default.ShoppingCart, Color(0xFF10b981)),
    Repairs("Repairs", Icons.Default.HomeRepairService, Color(0xFFf59e0b)),
    Cleaning("Cleaning", Icons.Default.CleaningServices, Color(0xFF9f86ff)),
    Bills("Bills", Icons.Default.Payment, Color(0xFFef4444)),
    Other("Other", Icons.Default.OtherHouses, Color(0xFF64748b)),
}
