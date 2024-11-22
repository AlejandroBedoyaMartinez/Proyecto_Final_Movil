package jano.net.proyecto_final

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Nav() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomePageScreen(navController) }
<<<<<<< HEAD
        composable("tareas") { (tareasScreen(navController, viewModel = viewModel())) }
        composable("notas") { (notasScreen(navController, viewModel = viewModel())) }
        composable("agregarNueva") { (AgregarNueva(viewModel = viewModel())) }
=======
        composable("tareas") { (tareasScreen(navController)) }
        composable("notas") { (notasScreen(navController)) }
        composable("agregarNueva") { (AgregarNueva()) }
        composable("AgregarNTarea") { (AgregarNuevaT()) }

>>>>>>> 5c533b3b2d7bee65f9a97f763631e4fa47dccb23
    }
}