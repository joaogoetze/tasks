package com.example.tasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tasks.view.task.CreateTask
import com.example.tasks.view.Home
import com.example.tasks.view.task.UpdateTask
import com.example.tasks.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()
            val viewModel: TaskViewModel = hiltViewModel()

            NavHost(
                startDestination = "home",
                navController = navController
            ) {
                composable(
                    route = "home"
                ) {
                    Home(navController = navController, viewModel = viewModel)
                }
                composable(
                    route = "createTask"
                ) {
                    CreateTask(navController = navController, viewModel = viewModel)
                }
                composable(
                    route = "updateTask/{uid}/{title}/{description}/{priority}/{deadline}",
                    arguments = listOf(
                        navArgument(name = "uid") {},
                        navArgument(name = "title") {},
                        navArgument(name = "description") {},
                        navArgument(name = "priority") {},
                        navArgument(name = "deadline") {}
                    )
                ) {
                    UpdateTask(
                        navController,
                        it.arguments?.getString("uid").toString(),
                        it.arguments?.getString("title").toString(),
                        it.arguments?.getString("description").toString(),
                        it.arguments?.getString("priority")!!.toInt(),
                        it.arguments?.getString("deadline").toString(),
                        viewModel
                    )
                }
            }
        }
    }
}