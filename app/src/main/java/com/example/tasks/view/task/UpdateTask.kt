package com.example.tasks.view.task

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tasks.model.Priorities
import com.example.tasks.viewmodel.TaskViewModel
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTask(
    navController: NavController,
    uid: String,
    title: String,
    description: String,
    priority: Int,
    deadline: String,
    viewModel: TaskViewModel = hiltViewModel()
) {

    var title by remember { mutableStateOf(title) }
    var description by remember { mutableStateOf(description) }
    var priority by remember { mutableStateOf(Priorities.values().first {
        it.value == priority
    }) }
    var deadline by remember { mutableStateOf(LocalDate.parse(deadline)) }

    var openDatePicker by remember { mutableStateOf(false) }
    val datePickerstate = rememberDatePickerState(
        initialSelectedDateMillis = deadline.toEpochDay() * 24L * 60 * 60 * 1000
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Create Task")
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                },
                label = {
                    Text(text = "Title")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
            )
            OutlinedTextField(
                value = description,
                onValueChange = {
                    description = it
                },
                label = {
                    Text(text = "Description")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Priority")

                RadioButton(
                    selected = priority == Priorities.LOW,
                    onClick = {
                        priority = Priorities.LOW
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color.Green
                    )
                )
                RadioButton(
                    selected = priority == Priorities.MEDIUM,
                    onClick = {
                        priority = Priorities.MEDIUM
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color.Yellow
                    )
                )
                RadioButton(
                    selected = priority == Priorities.HIGH,
                    onClick = {
                        priority = Priorities.HIGH
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color.Red
                    )
                )
            }
            OutlinedTextField(
                value = deadline.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                onValueChange = {},
                label = {
                    Text(text = "Deadline")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                interactionSource = remember {
                    MutableInteractionSource()
                }.also {
                    LaunchedEffect(it) {
                        it.interactions.collectLatest { interaction ->
                            if (interaction is PressInteraction.Release) {
                                openDatePicker = true
                            }
                        }
                    }
                },
                readOnly = true
            )
            AnimatedVisibility(openDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { openDatePicker = false },
                    confirmButton = {
                        Button(
                            onClick = {
                                datePickerstate.selectedDateMillis?.let { millis ->
                                    val epochDay = millis / (24 * 60 * 60 * 1000)
                                    deadline = LocalDate.ofEpochDay(epochDay)
                                }
                                openDatePicker = false
                            }
                        ) {
                            Text("Select")
                        }
                    }
                ) {
                    DatePicker(datePickerstate)
                }
            }
            Button(
                onClick = {
                    //TODO fazer validações antes de criar o objeto
                    viewModel.updateTask(uid.toInt(), title, description, priority.value, deadline)
                    //TODO Verficar o resultado para mostrar um toast na tela
                    navController.popBackStack()
                }
            ) {
                Text(text = "Update")
            }
            Button(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Text(text = "Cancel")
            }
        }
    }
}