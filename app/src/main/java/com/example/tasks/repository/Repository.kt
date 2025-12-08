package com.example.tasks.repository

import com.example.tasks.dao.TaskDao
import com.example.tasks.model.Task
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

@ViewModelScoped
class Repository @Inject constructor(private val taskDao: TaskDao){

    val getTasks: Flow<MutableList<Task>> = taskDao.getTasks()

    suspend fun createTask(task: Task) {
        taskDao.createTask(task = task)
    }

    suspend fun updateTask(uid: Int, title: String, description: String, priority: Int, deadline: LocalDate) {
        taskDao.updateTask(uid, title, description, priority, deadline)
    }

    suspend fun deleteTask(uid: Int) {
        taskDao.deleteTask(uid)
    }
}