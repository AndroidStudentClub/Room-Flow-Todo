package ru.androidschool.besttodo.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.androidschool.besttodo.data.model.Subtask
import ru.androidschool.besttodo.data.model.TaskEntity
import ru.androidschool.besttodo.data.model.TaskWithSubtasks

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks ORDER BY id DESC")
    fun getTasks(): Flow<List<TaskEntity>>

    @Transaction
    @Query("SELECT * FROM tasks ORDER BY id DESC")
    suspend fun getTasksWithSubtasks(): List<TaskWithSubtasks>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: TaskEntity): Long

    @Update
    suspend fun updateTask(task: TaskEntity): Int

    @Delete
    suspend fun deleteTask(task: TaskEntity): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSubTask(s: Subtask)

    @Transaction
    @Query("SELECT * FROM tasks WHERE id =:id ORDER BY id DESC")
    suspend fun getTasksById(id: Long): List<TaskWithSubtasks>
}