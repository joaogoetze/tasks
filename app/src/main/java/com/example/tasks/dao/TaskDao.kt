package com.example.tasks.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tasks.model.Task
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_table ORDER BY uid ASC")
    fun getTasks(): Flow<MutableList<Task>>

    @Insert
    suspend fun createTask(task: Task)

    @Query("UPDATE task_table SET title = :title, description = :description, priority = :priority, deadline = :deadline WHERE uid = :uid")
    suspend fun updateTask(uid: Int, title: String, description: String, priority: Int, deadline: LocalDate)

    @Query("DELETE FROM task_table WHERE uid = :uid")
    suspend fun deleteTask(uid: Int)
}