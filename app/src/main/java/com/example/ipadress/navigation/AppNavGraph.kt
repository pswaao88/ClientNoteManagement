package com.example.ipadress.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ipadress.feature.client.ClientDetailScreen
import com.example.ipadress.feature.client.ClientFormScreen
import com.example.ipadress.feature.department.DepartmentDetailScreen
import com.example.ipadress.feature.department.DepartmentFormScreen
import com.example.ipadress.feature.device.DeviceDetailScreen
import com.example.ipadress.feature.device.DeviceFormScreen
import com.example.ipadress.feature.home.HomeScreen
import com.example.ipadress.feature.pc.PcDetailScreen
import com.example.ipadress.feature.pc.PcFormScreen
import com.example.ipadress.presentation.AppViewModel

@Composable
fun AppNavGraph(viewModel: AppViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppDestination.Home.route,
    ) {
        composable(AppDestination.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                onClientClick = { navController.navigate(AppDestination.ClientDetail.createRoute(it)) },
                onAddClient = { navController.navigate(AppDestination.ClientForm.createRoute()) },
                onEditClient = { navController.navigate(AppDestination.ClientForm.createRoute(it)) },
            )
        }
        composable(
            route = AppDestination.ClientForm.route,
            arguments = listOf(navArgument("id") { type = NavType.LongType; defaultValue = 0L }),
        ) { entry ->
            val id = entry.arguments?.getLong("id") ?: 0L
            ClientFormScreen(
                clientId = id,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onDone = { navController.popBackStack() },
            )
        }
        composable(
            route = AppDestination.ClientDetail.route,
            arguments = listOf(navArgument("clientId") { type = NavType.LongType }),
        ) { entry ->
            val clientId = entry.arguments?.getLong("clientId") ?: return@composable
            ClientDetailScreen(
                clientId = clientId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onAddDepartment = { navController.navigate(AppDestination.DepartmentForm.createRoute(clientId)) },
                onDepartmentClick = {
                    navController.navigate(AppDestination.DepartmentDetail.createRoute(clientId, it))
                },
                onEditDepartment = {
                    navController.navigate(AppDestination.DepartmentForm.createRoute(clientId, it))
                },
            )
        }
        composable(
            route = AppDestination.DepartmentForm.route,
            arguments = listOf(
                navArgument("clientId") { type = NavType.LongType },
                navArgument("id") { type = NavType.LongType; defaultValue = 0L },
            ),
        ) { entry ->
            val clientId = entry.arguments?.getLong("clientId") ?: return@composable
            val id = entry.arguments?.getLong("id") ?: 0L
            DepartmentFormScreen(
                clientId = clientId,
                departmentId = id,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onDone = { navController.popBackStack() },
            )
        }
        composable(
            route = AppDestination.DepartmentDetail.route,
            arguments = listOf(
                navArgument("clientId") { type = NavType.LongType },
                navArgument("departmentId") { type = NavType.LongType },
            ),
        ) { entry ->
            val departmentId = entry.arguments?.getLong("departmentId") ?: return@composable
            DepartmentDetailScreen(
                departmentId = departmentId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onAddDevice = { navController.navigate(AppDestination.DeviceForm.createRoute(departmentId)) },
                onDeviceClick = {
                    navController.navigate(AppDestination.DeviceDetail.createRoute(departmentId, it))
                },
                onEditDevice = { navController.navigate(AppDestination.DeviceForm.createRoute(departmentId, it)) },
                onAddPc = { navController.navigate(AppDestination.PcForm.createRoute(departmentId)) },
                onPcClick = {
                    navController.navigate(AppDestination.PcDetail.createRoute(departmentId, it))
                },
                onEditPc = { navController.navigate(AppDestination.PcForm.createRoute(departmentId, it)) },
            )
        }
        composable(
            route = AppDestination.DeviceDetail.route,
            arguments = listOf(
                navArgument("departmentId") { type = NavType.LongType },
                navArgument("deviceId") { type = NavType.LongType },
            ),
        ) { entry ->
            val deviceId = entry.arguments?.getLong("deviceId") ?: return@composable
            DeviceDetailScreen(
                deviceId = deviceId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onEdit = { navController.navigate(AppDestination.DeviceForm.createRoute(it.first, it.second)) },
            )
        }
        composable(
            route = AppDestination.DeviceForm.route,
            arguments = listOf(
                navArgument("departmentId") { type = NavType.LongType },
                navArgument("id") { type = NavType.LongType; defaultValue = 0L },
            ),
        ) { entry ->
            val departmentId = entry.arguments?.getLong("departmentId") ?: return@composable
            val id = entry.arguments?.getLong("id") ?: 0L
            DeviceFormScreen(
                departmentId = departmentId,
                deviceId = id,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onDone = { navController.popBackStack() },
            )
        }
        composable(
            route = AppDestination.PcDetail.route,
            arguments = listOf(
                navArgument("departmentId") { type = NavType.LongType },
                navArgument("pcId") { type = NavType.LongType },
            ),
        ) { entry ->
            val pcId = entry.arguments?.getLong("pcId") ?: return@composable
            PcDetailScreen(
                pcId = pcId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onEdit = { navController.navigate(AppDestination.PcForm.createRoute(it.first, it.second)) },
            )
        }
        composable(
            route = AppDestination.PcForm.route,
            arguments = listOf(
                navArgument("departmentId") { type = NavType.LongType },
                navArgument("id") { type = NavType.LongType; defaultValue = 0L },
            ),
        ) { entry ->
            val departmentId = entry.arguments?.getLong("departmentId") ?: return@composable
            val id = entry.arguments?.getLong("id") ?: 0L
            PcFormScreen(
                departmentId = departmentId,
                pcId = id,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onDone = { navController.popBackStack() },
            )
        }
    }
}
