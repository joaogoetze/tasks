package com.example.tasks.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tasks.model.Task

@Composable
fun TaskItem(
    task: Task
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(5.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(5.dp)
        ) {
            Text(text = task.title.toString())
            Text(text = task.description.toString())
            Text(text = task.priority.toString())
            Text(text = task.deadLine.toString())
        }
    }
}