package org.example.homeflow.app

import org.example.homeflow.core.data.HouseRepositoryImpl
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sunildhiman90.kmauth.core.KMAuthConfig
import com.sunildhiman90.kmauth.core.KMAuthInitializer
import com.sunildhiman90.kmauth.google.KMAuthGoogle.googleAuthManager
import org.example.homeflow.AppConstants
import org.example.homeflow.core.data.AuthRepositoryImpl
import org.example.homeflow.core.data.MembershipRepositoryImpl
import org.example.homeflow.core.data.TaskRepositoryImpl
import org.example.homeflow.core.datastore.createPreferencesDataStore
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
    val datastore = remember { createPreferencesDataStore() }

    val houseRepository = HouseRepositoryImpl(dataStore = datastore)
    val taskRepository = TaskRepositoryImpl()
    val membershipRepository = MembershipRepositoryImpl()
    val authRepository = AuthRepositoryImpl(dataStore = datastore, googleAuthManager = googleAuthManager)
    val isLoggedIn by authRepository.isSignedIn.collectAsState(false)



    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) HomeRoute else LoginRoute
    ) {
        composable<LoginRoute> {
            val vm = viewModel<LoginViewModel> { LoginViewModel(authRepository) }
            LoginScreen(
                viewModel = vm,
                onLogin = { navController.navigate(HomeRoute) },
            )
        }

        composable<HomeRoute> {
            val vm = viewModel<HomeViewModel>{ HomeViewModel(houseRepository, authRepository) }
            HomeScreen(
                viewModel = vm,
                onHouseClick = { id -> navController.navigate(TasksRoute(id)) },
                onLogout = { navController.popBackStack(route = LoginRoute, inclusive = false) },
            )
        }

        composable<AddTaskRoute> {
            val route = it.toRoute<AddTaskRoute>()
            val vm = viewModel<AddTaskViewModel> { AddTaskViewModel(houseId = route.houseId, taskRepository, membershipRepository) }

            AddTaskScreen(
                viewModel = vm,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable<TasksRoute> {
            val route = it.toRoute<TasksRoute>()
            val vm = viewModel<TasksViewModel> {
                TasksViewModel(
                    houseId = route.id,
                    houseRepository = houseRepository,
                    taskRepository = taskRepository
                )
            }

            TasksScreen(
                viewModel = vm,
                onAddTask = { houseId -> navController.navigate(AddTaskRoute(houseId)) },
                onEditTask = {houseId, taskId -> navController.navigate(EditTaskRoute(houseId, taskId))},
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable<EditTaskRoute> {
            val route = it.toRoute<EditTaskRoute>()
            val vm = viewModel<EditTaskViewModel> { EditTaskViewModel(houseId = route.houseId, taskId = route.taskId,  taskRepository, membershipRepository) }
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

