package org.example.homeflow.app

import HouseRepositoryImpl
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.gitlive.firebase.Firebase
import org.example.homeflow.core.data.TaskRepositoryImpl
import org.example.homeflow.core.ui.theme.HomeFlowTheme
import org.example.homeflow.feature.add_task.AddTaskScreen
import org.example.homeflow.feature.add_task.AddTaskViewModel
import org.example.homeflow.feature.authentication.LoginScreen
import org.example.homeflow.feature.home.HomeScreen
import org.example.homeflow.feature.home.HomeViewModel
import org.example.homeflow.feature.house.HouseScreen
import org.example.homeflow.feature.house.HouseViewModel
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
    val houseRepository = HouseRepositoryImpl()
    val taskRepository = TaskRepositoryImpl()

    //val logged = Firebase

    NavHost(
        navController = navController,
        startDestination = HomeRoute
    ) {
        composable<LoginRoute> {
            LoginScreen(
                onLogin = { navController.navigate(LoginRoute) },
            )
        }

        composable<HomeRoute> {
            HomeScreen(
                viewModel = HomeViewModel(houseRepository),
                onHouseClick = { id -> navController.navigate(HouseRoute(id)) },
            )
        }

        composable<AddTaskRoute> {
            val route = it.toRoute<AddTaskRoute>()
            val vm = viewModel<AddTaskViewModel> { AddTaskViewModel(houseId = route.houseId, taskRepository) }

            AddTaskScreen(
                viewModel = vm,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable<HouseRoute> {
            val route = it.toRoute<HouseRoute>()
            val vm = viewModel<HouseViewModel> { HouseViewModel(houseId = route.id, houseRepository) }

            HouseScreen(
                viewModel = vm,
                onAddTask = { houseId -> navController.navigate(AddTaskRoute(houseId)) },
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

@Preview
@Composable
fun AppPreview() {
    App()
}

