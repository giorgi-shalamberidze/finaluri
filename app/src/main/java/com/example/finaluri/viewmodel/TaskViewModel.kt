package com.example.finaluri.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.finaluri.data.AppDatabase
import com.example.finaluri.data.TaskEntity
import com.example.finaluri.data.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = TaskRepository(
        AppDatabase.getInstance(application).taskDao()
    )

    val tasks: StateFlow<List<TaskEntity>> = repository.tasks.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun addTask(title: String, description: String): Boolean {
        if (title.isBlank()) {
            return false
        }

        viewModelScope.launch {
            repository.addTask(title, description)
        }
        return true
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }
}
