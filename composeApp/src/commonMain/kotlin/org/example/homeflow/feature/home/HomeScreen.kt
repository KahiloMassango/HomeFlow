package org.example.homeflow.feature.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.homeflow.core.model.House
import org.example.homeflow.core.ui.components.HomeFlowButton
import org.example.homeflow.core.ui.components.HomeFlowOutlinedButton
import org.example.homeflow.feature.home.components.CreateHouseholdBottomSheet
import org.example.homeflow.feature.home.components.HomeTopBar
import org.example.homeflow.feature.home.components.HouseholdCard
import org.example.homeflow.feature.home.components.JoinHouseholdBottomSheet

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onHouseClick: (String) -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()
    val houses by viewModel.houses.collectAsState()

    var showCreateSheet by remember { mutableStateOf(false) }
    var showJoinSheet by remember { mutableStateOf(false) }

    HomeScreenContent(
        houses = houses,
        onHouseClick = { id -> onHouseClick(id) },
        onCreateNew = { showCreateSheet = true },
        onJoinInvite = { showJoinSheet = true }
    )


    if (showCreateSheet) {
        CreateHouseholdBottomSheet(
            isLoading = uiState.isLoading,
            onDismiss = {
                viewModel.clearHouseCode()
                showCreateSheet = false
            },
            onCreate = { name ->
                viewModel.createHouse(name)
                showCreateSheet = false
            }
        )
    }
    if (showJoinSheet) {
        JoinHouseholdBottomSheet(
            isLoading = uiState.isLoading,
            onDismiss = { showJoinSheet = false },
            onJoin = { code ->
                viewModel.joinHouse(code)
                showJoinSheet = false
            }
        )
    }
}

@Composable
private fun HomeScreenContent(
    username: String = "John Doe",
    houses: List<House>,
    onHouseClick: (String) -> Unit,
    onCreateNew: () -> Unit,
    onJoinInvite: () -> Unit
) {

    Scaffold(
        topBar = { HomeTopBar(username = username) }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.surface,
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
            ) {
                houses.forEach { house ->
                    HouseholdCard(
                        modifier = Modifier.padding(top = 16.dp),
                        house = house,
                        onClick = { id -> onHouseClick(id) }
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
