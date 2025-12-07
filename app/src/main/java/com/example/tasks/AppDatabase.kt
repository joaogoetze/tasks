package com.example.tasks

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tasks.dao.TaskDao
import com.example.tasks.model.Task
import com.example.tasks.utils.Converters

@Database(entities = [Task::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
}