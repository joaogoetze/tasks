package com.example.tasks.components

import android.app.AlertDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tasks.model.Task
import com.example.tasks.viewmodel.TaskViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun TaskItem(
    task: Task,
    viewModel: TaskViewModel = hiltViewModel(),
    navController: NavController
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val uid = task.uid

    fun deleteTaskAlertDialog() {
        val alertDialog = AlertDialog.Builder(context)

        alertDialog.setTitle("Task deletion").setMessage("Are you shure yoy wan't delete this task?")
        alertDialog.setPositiveButton("Delete") { _, _ ->
            scope.launch(Dispatchers.IO) {
                viewModel.deleteTask(uid)
            }
            scope.launch(Dispatchers.Main) {
                navController.navigate("home")
            }
        }
        alertDialog.setNegativeButton("Cancel") { _, _ ->
            alertDialog
        }
        alertDialog.show()
    }

    Card(
        modifier = Modifier.fillMaxWidth().padding(5.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(5.dp)
        ) {
            Text(text = task.title.toString())
            Text(text = task.description.toString())
            Text(text = task.priority.toString())
            Text(text = task.deadline.toString())
            IconButton(
                onClick = {
                    deleteTaskAlertDialog()
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Delete task button"
                )
            }
            IconButton(
                onClick = {
                    val deadlineString = task.deadline.toString()
                    navController.navigate(
                        "updateTask/${task.uid}/${task.title}/${task.description}/${task.priority}/$deadlineString"
                    )
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "Edit task button"
                )
            }
        }
    }
}