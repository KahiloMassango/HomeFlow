package org.example.homeflow.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.homeflow.core.ui.theme.HomeFlowTheme
import org.example.homeflow.feature.add_task.AddTaskScreen
import org.example.homeflow.feature.authentication.LoginScreen
import org.example.homeflow.feature.home.HomeScreen
import org.example.homeflow.feature.household.HouseholdScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    HomeFlowTheme {
        MainApp()
    }
}

@Composable
@Preview
fun MainApp(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = AddTaskRoute("g")
    ) {
        composable<LoginRoute> {
            LoginScreen(
                onLogin = { navController.navigate(LoginRoute) },
            )
        }

        composable<HomeRoute> {
            HomeScreen(
                onHouseholdClick = { id -> navController.navigate(HouseholdDetailRoute(id)) },
            )
        }

        composable<AddTaskRoute> { AddTaskScreen() }

        composable<HouseholdDetailRoute> { HouseholdScreen(
            onAddTask = { householdId -> navController.navigate(AddTaskRoute(householdId)) },
        ) }
    }
}

@Preview
@Composable
fun AppPreview() {
    App()
}

