package com.example.tasks.ui.theme.componentscolors

import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.tasks.ui.theme.PrimaryGreen
import com.example.tasks.ui.theme.PrimaryGreenSoft

@Composable
fun TextFieldColors() =
    OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color.Gray,
        unfocusedTextColor = Color.Gray,
        focusedLabelColor = PrimaryGreen,
        unfocusedLabelColor = Color.Gray,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        focusedBorderColor = PrimaryGreenSoft,
        unfocusedBorderColor = Color.Gray,
        cursorColor = Color.Gray,
        focusedLeadingIconColor = Color.Gray,
        unfocusedLeadingIconColor = Color.Gray,
    )
