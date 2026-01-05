package com.example.tasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasks.model.SortType
import com.example.tasks.model.Task
import com.example.tasks.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private val _sortType = MutableStateFlow(SortType.DATE)

    val tasks: StateFlow<List<Task>> = repository.getTasks
        .combine(_sortType) { tasks, sort ->
            val comparator = when (sort) {
                SortType.DATE ->
                    compareBy<Task> { it.completed }
                        .thenBy { it.deadline }
                SortType.PRIORITY ->
                    compareBy<Task> { it.completed }
                        .thenByDescending { it.priority }
            }

            tasks.sortedWith(comparator)
        }
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun setSortType(type: SortType) {
        _sortType.value = type
    }

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

    fun completeTask(uid: Int, complete: Boolean) {
        viewModelScope.launch {
            repository.updateTaskCompletion(uid, complete)
        }
    }
}