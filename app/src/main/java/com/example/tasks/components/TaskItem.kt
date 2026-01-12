package com.example.tasks.components

import android.app.AlertDialog
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tasks.model.Task
import com.example.tasks.ui.theme.CardBackground
import com.example.tasks.ui.theme.PrimaryGreen
import com.example.tasks.ui.theme.PriorityHigh
import com.example.tasks.ui.theme.PriorityLow
import com.example.tasks.ui.theme.PriorityMedium
import com.example.tasks.viewmodel.TaskViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    task: Task,
    viewModel: TaskViewModel = hiltViewModel(),
    navController: NavController
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val uid = task.uid
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    val animatedAlpha by animateFloatAsState(
        targetValue = if (task.completed) 0.5f else 1f,
        label = "contentAlpha"
    )

    val priorityIndicatorColor = if (task.completed) {
        Color.Gray
    } else {
        when(task.priority) {
            1 -> PriorityLow
            2 -> PriorityMedium
            3 -> PriorityHigh
            else -> Color.Gray
        }
    }

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
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .graphicsLayer { alpha = animatedAlpha },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp)
            ) {
                Text(
                    text = task.title.toString(),
                    color = PrimaryGreen,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    textDecoration = if (task.completed)
                        TextDecoration.LineThrough
                    else null
                )
                Text(
                    text = task.description.toString(),
                    fontSize = 14.sp,
                    maxLines = 2,
                    color = Color.Gray,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(15.dp)
                            .background(priorityIndicatorColor, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = task.deadline.format(dateFormatter),
                        color = Color.Gray)
                }
            }
            Row {
                IconButton(
                    onClick = {
                        val deadlineString = task.deadline.toString()
                        navController.navigate(
                            "updateTask/${task.uid}/${task.title}/${task.description}/${task.priority}/$deadlineString/${task.completed}"
                        )
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Edit task button",
                        tint = Color.Gray
                    )
                }
                IconButton(
                    onClick = {
                        deleteTaskAlertDialog()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Delete task button",
                        tint = Color.Gray
                    )
                }
                Checkbox(
                    checked = task.completed,
                    onCheckedChange = { isChecked ->
                        viewModel.completeTask(
                            task.uid, isChecked
                        )
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Gray,
                        uncheckedColor = Color.Gray,
                        checkmarkColor = Color.White
                    )
                )
            }
        }
    }
}