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
import org.example.homeflow.core.data.TaskRepositoryImpl
import org.example.homeflow.core.datastore.createPreferencesDataStore
import org.example.homeflow.core.ui.theme.HomeFlowTheme
import org.example.homeflow.feature.add_task.AddTaskScreen
import org.example.homeflow.feature.add_task.AddTaskViewModel
import org.example.homeflow.feature.authentication.LoginScreen
import org.example.homeflow.feature.authentication.LoginViewModel
import org.example.homeflow.feature.home.HomeScreen
import org.example.homeflow.feature.home.HomeViewModel
import org.example.homeflow.feature.house.HouseScreen
import org.example.homeflow.feature.house.HouseViewModel
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
            val vm = viewModel<HouseViewModel> {
                HouseViewModel(
                    houseId = route.id,
                    houseRepository = houseRepository,
                    taskRepository = taskRepository
                )
            }

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

