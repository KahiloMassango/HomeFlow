package org.example.homeflow.feature.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.homeflow.core.model.HouseWithMembers
import org.example.homeflow.core.ui.components.HomeFlowButton
import org.example.homeflow.core.ui.components.HomeFlowOutlinedButton
import org.example.homeflow.feature.home.components.CreateHouseholdBottomSheet
import org.example.homeflow.feature.home.components.HomeTopBar
import org.example.homeflow.feature.home.components.HouseCard
import org.example.homeflow.feature.home.components.JoinHouseholdBottomSheet

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onHouseClick: (String) -> Unit,
    onLogout: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()
    val houses by viewModel.houses.collectAsState()

    var showCreateSheet by remember { mutableStateOf(false) }
    var showJoinSheet by remember { mutableStateOf(false) }

    LaunchedEffect(uiState) {
        if (uiState.houseCreated) {
            showCreateSheet = false
            viewModel.clearHouseCreatedAndJoined()
        }

        if (uiState.houseJoined) {
            showJoinSheet = false
            viewModel.clearHouseCreatedAndJoined()
        }

    }

    HomeScreenContent(
        houses = houses,
        isLoading = uiState.isLoading,
        onHouseClick = { id -> onHouseClick(id) },
        onCreateNew = { showCreateSheet = true },
        onJoinInvite = { showJoinSheet = true },
        onLogout = {
            viewModel.logout()
            onLogout()
        }
    )


    if (showCreateSheet) {
        CreateHouseholdBottomSheet(
            isLoading = uiState.isLoading,
            onDismiss = {
                viewModel.clearHouseCode()
            },
            onCreate = { name ->
                viewModel.createHouse(name)
            }
        )
    }
    if (showJoinSheet) {
        JoinHouseholdBottomSheet(
            isLoading = uiState.isLoading,
            onDismiss = { showJoinSheet = false },
            onJoin = { code ->
                viewModel.joinHouse(code)
            }
        )
    }
}

@Composable
private fun HomeScreenContent(
    houses: List<HouseWithMembers>,
    isLoading: Boolean,
    onHouseClick: (String) -> Unit,
    onCreateNew: () -> Unit,
    onJoinInvite: () -> Unit,
    onLogout: () -> Unit
) {

    Scaffold(
        topBar = { HomeTopBar(
            onLogout = onLogout
        ) }
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
                houses.forEach {
                    HouseCard(
                        modifier = Modifier.padding(top = 16.dp),
                        house = it.house,
                        isOwner = it.isOwner,
                        onClick = { id -> onHouseClick(id) }
                    )
                }

                Spacer(Modifier.height(30.dp))

                HomeFlowButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    isLoading = isLoading,
                    onClick = onCreateNew,
                    text = "Create house",
                )

                Spacer(Modifier.height(14.dp))

                // Outline button
                HomeFlowOutlinedButton(
                    onClick = onJoinInvite,
                    text = "Join house with invite code",
                    isLoading = isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}
