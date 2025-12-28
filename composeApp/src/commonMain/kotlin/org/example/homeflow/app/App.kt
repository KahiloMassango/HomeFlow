package org.example.homeflow.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sunildhiman90.kmauth.core.KMAuthConfig
import com.sunildhiman90.kmauth.core.KMAuthInitializer
import org.example.homeflow.AppConstants
import org.example.homeflow.core.data.repositories.AuthRepository
import org.example.homeflow.core.ui.theme.HomeFlowTheme
import org.example.homeflow.feature.add_task.AddTaskScreen
import org.example.homeflow.feature.add_task.AddTaskViewModel
import org.example.homeflow.feature.authentication.LoginScreen
import org.example.homeflow.feature.authentication.LoginViewModel
import org.example.homeflow.feature.edit_task.EditTaskScreen
import org.example.homeflow.feature.edit_task.EditTaskViewModel
import org.example.homeflow.feature.home.HomeScreen
import org.example.homeflow.feature.home.HomeViewModel
import org.example.homeflow.feature.tasks.TasksScreen
import org.example.homeflow.feature.tasks.TasksViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.koin.mp.KoinPlatform

@Composable
@Preview
fun App() {
    KMAuthInitializer.initialize(KMAuthConfig(webClientId = AppConstants.WEB_CLIENT_ID))
    HomeFlowTheme {
        MainApp()
    }
}

@Composable
@Preview
fun MainApp(
    navController: NavHostController = rememberNavController(),
) {

    val authRepository = KoinPlatform.getKoin().get<AuthRepository>()
    val isLoggedIn by authRepository.isSignedIn.collectAsState(false)

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) HomeRoute else LoginRoute
    ) {
        composable<LoginRoute> {
            val vm = koinViewModel<LoginViewModel>()
            LoginScreen(
                viewModel = vm,
                onLogin = { navController.navigate(HomeRoute) },
            )
        }

        composable<HomeRoute> {
            val vm = koinViewModel<HomeViewModel>()
            HomeScreen(
                viewModel = vm,
                onHouseClick = { id -> navController.navigate(TasksRoute(id)) },
                onLogout = { navController.popBackStack(route = LoginRoute, inclusive = false) },
            )
        }

        composable<AddTaskRoute> {
            val route = it.toRoute<AddTaskRoute>()
            val vm = koinViewModel<AddTaskViewModel>(parameters = { parametersOf(route.houseId) })

            AddTaskScreen(
                viewModel = vm,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable<TasksRoute> {
            val route = it.toRoute<TasksRoute>()
            val vm = koinViewModel<TasksViewModel>(parameters = { parametersOf(route.id) })

            TasksScreen(
                viewModel = vm,
                onAddTask = { houseId -> navController.navigate(AddTaskRoute(houseId)) },
                onEditTask = {houseId, taskId -> navController.navigate(EditTaskRoute(houseId, taskId))},
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable<EditTaskRoute> {
            val route = it.toRoute<EditTaskRoute>()
            val vm = koinViewModel<EditTaskViewModel>(parameters = { parametersOf(route.taskId) })
            EditTaskScreen(
                viewModel = vm,
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

