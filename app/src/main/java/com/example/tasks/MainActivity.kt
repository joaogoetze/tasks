package com.example.tasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.tasks.view.CreateTask
import com.example.tasks.view.Home
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()

            NavHost(
                startDestination = "home",
                navController = navController
            ) {
                composable(
                    route = "home"
                ) {
                    Home(navController = navController)
                }
                composable(
                    route = "createTask"
                ) {
                    CreateTask(navController = navController)
                }
            }
        }
    }
}