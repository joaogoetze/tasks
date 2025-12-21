package com.example.tasks.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.tasks.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_table ORDER BY uid ASC")
    fun getTasks(): Flow<MutableList<Task>>

    @Insert
    suspend fun createTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Query("DELETE FROM task_table WHERE uid = :uid")
    suspend fun deleteTask(uid: Int)
}