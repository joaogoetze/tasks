package com.example.tasks.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tasks.constraints.Constraints
import java.time.LocalDate

@Entity(tableName = Constraints.TASK_TABLE)
data class Task(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid") val uid: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "priority") val priority: Int,
    @ColumnInfo(name = "deadline") val deadline: LocalDate
)