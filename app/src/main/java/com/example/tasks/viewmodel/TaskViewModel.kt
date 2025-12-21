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
    val sortType: StateFlow<SortType> = _sortType

    val tasks: StateFlow<List<Task>> = repository.getTasks
        .combine(_sortType) { tasks, sort ->
            when (sort) {
                SortType.DATE -> tasks.sortedBy { it.deadline }
                SortType.PRIORITY -> tasks.sortedByDescending { it.priority }
            }
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
}