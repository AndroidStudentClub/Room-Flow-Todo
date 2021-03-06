package ru.androidschool.besttodo.data.repository

import android.content.Context
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.androidschool.besttodo.data.database.TaskDao
import ru.androidschool.besttodo.data.database.TaskDatabase
import ru.androidschool.besttodo.data.model.TaskEntity
import ru.androidschool.besttodo.domain.repository.TaskRepository

class TasksRepositoryImpl(
    context: Context,
    private val backgroundDispatcher: CoroutineDispatcher
) : TaskRepository {

    private val taskDao: TaskDao

    init {
        val database = TaskDatabase.getInstance(context, backgroundDispatcher)
        taskDao = database!!.taskDao()
    }

    override suspend fun insert(task: TaskEntity) {
        withContext(backgroundDispatcher) {
            taskDao.insertTask(task)
        }
    }

    override suspend fun update(task: TaskEntity) {
        withContext(backgroundDispatcher) { taskDao.updateTask(task) }
    }

    override suspend fun delete(task: TaskEntity) {
        withContext(backgroundDispatcher) { taskDao.deleteTask(task) }
    }

    override fun getAllTasks(): Flow<List<TaskEntity>> {
        return taskDao.getTasks()
    }
}