package ru.androidschool.besttodo.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.androidschool.besttodo.data.model.Subtask
import ru.androidschool.besttodo.data.model.Task
import ru.androidschool.besttodo.data.model.TaskEntity

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks ORDER BY id DESC")
    fun getTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks ORDER BY id DESC")
    suspend fun getTasksWithSubtasks(): List<Task>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: TaskEntity): Long

    @Update
    suspend fun updateTask(task: TaskEntity): Int

    @Delete
    suspend fun deleteTask(task: TaskEntity): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSubTask(s: Subtask)

    @Query("SELECT * FROM tasks WHERE id =:id ORDER BY id DESC")
    suspend fun getTasksById(id: Int): List<Task>
}