package org.example.homeflow.feature.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.homeflow.core.model.HouseholdItem
import org.example.homeflow.core.ui.components.HomeFlowButton
import org.example.homeflow.core.ui.components.HomeFlowOutlinedButton
import org.example.homeflow.feature.home.components.CreateHouseholdBottomSheet
import org.example.homeflow.feature.home.components.HomeTopBar
import org.example.homeflow.feature.home.components.HouseholdCard
import org.example.homeflow.feature.home.components.JoinHouseholdBottomSheet

@Composable
fun HomeScreen(
    onHouseholdClick: (String) -> Unit,
) {

    var showCreateSheet by remember { mutableStateOf(false) }
    var showJoinSheet by remember { mutableStateOf(false) }

    HomeScreenContent(
        households = listOf(
            HouseholdItem("Family Home", 4, 12),
            HouseholdItem("Apartment 4B", 2, 5)
        ),
        onHouseholdClick = { id -> onHouseholdClick(id) },
        onCreateNew = { showCreateSheet = true },
        onJoinInvite = { showJoinSheet = true }
    )

    if (showCreateSheet) {
        CreateHouseholdBottomSheet(
            onDismiss = { showCreateSheet = false },
            onCreate = { name ->
                // Handle create household
                showCreateSheet = false
            }
        )
    }
    if (showJoinSheet) {
       JoinHouseholdBottomSheet(
            onDismiss = { showJoinSheet = false },
            onJoin = { code ->
                // Handle create household
                showJoinSheet = false
            }
        )
    }
}

@Composable
private fun HomeScreenContent(
    username: String = "John Doe",
    households: List<HouseholdItem>,
    onHouseholdClick: (String) -> Unit,
    onCreateNew: () -> Unit,
    onJoinInvite: () -> Unit
) {

    Scaffold(
        topBar = { HomeTopBar(username = username) }
    ) { paddingValues ->
        Surface (
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.surface,
        ){
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
            ) {
                households.forEach { item ->
                    HouseholdCard(
                        modifier = Modifier.padding(top = 16.dp),
                        household = item,
                        onClick = { onHouseholdClick("") }
                    )
                }

                Spacer(Modifier.height(30.dp))

                HomeFlowButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = onCreateNew,
                ) {
                    Text("Create New Household")
                }

                Spacer(Modifier.height(14.dp))

                // Outline button
                HomeFlowOutlinedButton(
                    onClick = onJoinInvite,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text("Join with Invite Code")
                }
            }
        }
    }
}
