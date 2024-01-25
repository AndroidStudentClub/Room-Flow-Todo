package ru.androidschool.besttodo.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.androidschool.besttodo.data.model.Task
import ru.androidschool.besttodo.data.model.TaskEntity

interface TaskRepository {

    fun getAllTasks(): Flow<List<TaskEntity>>
    suspend fun getTasksWithSubTasks(id: Int): List<Task>
    suspend fun  insert(task: TaskEntity)
    suspend fun  update(task: TaskEntity)
    suspend fun  delete(task: TaskEntity)
}