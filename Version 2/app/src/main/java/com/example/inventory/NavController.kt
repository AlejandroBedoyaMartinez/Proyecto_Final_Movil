package com.example.inventory

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.inventory.ui.nota.editarNota
import com.example.inventory.ui.nota.notasScreen
import com.example.inventory.ui.nota.verNota
import com.example.inventory.ui.nota.viewModelNota
import com.example.inventory.ui.tarea.ViewModelTarea
import com.example.inventory.ui.tarea.editarTarea
import com.example.inventory.ui.tarea.tareasScreen
import com.example.inventory.ui.tarea.verTarea

@Composable
fun Nav(viewModelNota: viewModelNota,viewModelTarea: ViewModelTarea) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomePageScreen(navController) }
        composable("tareas") { (tareasScreen(navController, viewModelTarea)) }
        composable("notas") { (notasScreen(navController, viewModelNota)) }
        composable("agregarNueva") { (AgregarNueva(navController,viewModelNota,viewModelTarea)) }
        composable("editarNota/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull() ?: 0
            editarNota(navController, viewModelNota, noteId)
        }
        composable("editarTarea/{tareaId}") { backStackEntry ->
            val tareaId = backStackEntry.arguments?.getString("tareaId")?.toIntOrNull() ?: 0
            editarTarea(navController, viewModelTarea, tareaId)
        }
        composable("verNota/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull() ?: 0
            verNota(navController, viewModelNota, noteId)
        }
        composable("verTarea/{tareaId}") { backStackEntry ->
            val tareaId = backStackEntry.arguments?.getString("tareaId")?.toIntOrNull() ?: 0
            verTarea(navController, viewModelTarea, tareaId)
        }
    }
}