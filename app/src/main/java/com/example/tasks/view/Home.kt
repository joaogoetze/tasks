package com.example.tasks.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tasks.components.TaskItem
import com.example.tasks.model.SortType
import com.example.tasks.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navController: NavController,
    viewModel: TaskViewModel = hiltViewModel()
) {

    var filterMenuState by remember { mutableStateOf(false) }
    var sortType by remember { mutableStateOf(SortType.DATE) }

    val tasksArray by viewModel.tasks.collectAsState()
    val sortedTasks = remember(tasksArray, sortType) {
        when (sortType) {
            SortType.DATE -> tasksArray.sortedBy { it.deadline }
            SortType.PRIORITY -> tasksArray.sortedByDescending { it.priority }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Tasks")
                },
                actions = {
                    Icon(
                        imageVector = Icons.Rounded.List,
                        contentDescription = "Filter list dropdown menu",
                        modifier = Modifier.clickable{
                            filterMenuState = true
                        }
                    )
                    Column(
                        modifier = Modifier
                    ) {
                        DropdownMenu(
                            expanded = filterMenuState,
                            onDismissRequest = {
                                filterMenuState = false
                            },
                            modifier = Modifier.width(200.dp)
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Text(text = "Date")
                                },
                                onClick = {
                                    sortType = SortType.DATE
                                    filterMenuState = false
                                }
                            )
                            DropdownMenuItem(
                                text = {
                                    Text(text = "Priority")
                                },
                                onClick = {
                                    sortType = SortType.PRIORITY
                                    filterMenuState = false
                                }
                            )
                        }
                    }
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
                itemsIndexed(sortedTasks) { _, task -> task.uid
                    TaskItem(
                        task = task,
                        viewModel = viewModel,
                        navController = navController
                    )
                }
            }
        }
    }
}