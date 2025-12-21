package com.example.tasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasks.model.Task
import com.example.tasks.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    val tasks: StateFlow<List<Task>> = repository.getTasks.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun createTask(task: Task) {
        viewModelScope.launch {
            repository.createTask(task = task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task = task)
        }
    }

    fun deleteTask(uid: Int) {
        viewModelScope.launch {
            repository.deleteTask(uid)
        }
    }
}