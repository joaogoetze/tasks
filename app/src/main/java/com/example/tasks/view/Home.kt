package com.example.tasks.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tasks.components.TaskItem
import com.example.tasks.model.Task
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navController: NavController
) {

    val tasksArray = mutableListOf(
        Task(title = "Task Title 1", description = "Task description 1", priority = 1, deadline = LocalDate.of(2025, 12, 20)),
        Task(title = "Task Title 2", description = "Task description 2", priority = 2, deadline = LocalDate.of(2025, 12, 21)),
        Task(title = "Task Title 3", description = "Task description 3", priority = 3, deadline = LocalDate.of(2025, 12, 22))
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Tasks")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("createTask")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create task button"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier.padding(5.dp)
            ) {
                itemsIndexed(tasksArray) { _, task ->
                    TaskItem(task = task)
                }
            }
        }
    }
}