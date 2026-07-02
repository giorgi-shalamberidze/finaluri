package com.example.finaluri.data

import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {
    val tasks: Flow<List<TaskEntity>> = taskDao.observeTasks()

    suspend fun addTask(title: String, description: String) {
        taskDao.insertTask(
            TaskEntity(
                title = title.trim(),
                description = description.trim()
            )
        )
    }

    suspend fun deleteTask(task: TaskEntity) {
        taskDao.deleteTask(task)
    }
}
