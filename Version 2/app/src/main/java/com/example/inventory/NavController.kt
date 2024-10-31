package com.example.inventory

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Nav(viewModel: viewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomePageScreen(navController) }
        composable("tareas") { (tareasScreen(navController, viewModel)) }
        composable("notas") { (notasScreen(navController, viewModel)) }
        composable("agregarNueva") { (AgregarNueva(navController,viewModel)) }
    }
}