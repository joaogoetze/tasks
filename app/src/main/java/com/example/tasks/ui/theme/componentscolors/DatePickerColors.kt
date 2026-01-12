package com.example.tasks.ui.theme.componentscolors

import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.tasks.ui.theme.Background
import com.example.tasks.ui.theme.PrimaryGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerColors() =
    DatePickerDefaults.colors(
        containerColor = Background,
        titleContentColor = Color.Gray,
        dayContentColor = Color.Gray,
        headlineContentColor = Color.Black,
        weekdayContentColor = Color.DarkGray,
        subheadContentColor = Color.Gray,
        yearContentColor = Color.Black,
        currentYearContentColor = Color.Gray,
        selectedYearContentColor = Color.Gray,
        selectedYearContainerColor = Color.Gray,
        selectedDayContainerColor = PrimaryGreen,
        selectedDayContentColor = Color.White,
        todayDateBorderColor = PrimaryGreen
    )