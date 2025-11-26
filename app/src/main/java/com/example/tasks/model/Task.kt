package com.example.tasks.model

import java.time.LocalDate

data class Task(
    val title: String? = null,
    val description: String? = null,
    val priority: Int? = null,
    val deadline: LocalDate? = null
)