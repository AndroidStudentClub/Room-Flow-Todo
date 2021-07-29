package ru.androidschool.besttodo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.androidschool.besttodo.data.model.TaskEntity
import ru.androidschool.besttodo.data.model.TaskCategory

@RunWith(AndroidJUnit4::class)
class TaskDaoTest {

    // 1
    @get:Rule
    val databaseRule = DatabaseRule()

    // 2
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun insertTaskTest() {
        // 3
        val testTask = TaskEntity(5, "Прочитать Совершенный код", false, TaskCategory.STUDY)
        // 4
        databaseRule.appDatabase.taskDao().insertTask(testTask)
        // 5
        val taskSize = databaseRule.appDatabase.taskDao().getTasks().blockingObserve()?.size
        // 6
        assertEquals(taskSize, 1)
    }

    @Test
    fun updateTaskTest() {
        val testTask = TaskEntity(5, "Прочитать Совершенный код", false, TaskCategory.STUDY)
        databaseRule.appDatabase.taskDao().insertTask(testTask)
        val newTitle = "Прочитать книгу Рефакторинг"
        val updatedTask = testTask.copy(title = newTitle)

        databaseRule.appDatabase.taskDao().updateTask(updatedTask)

        val actualTitle = databaseRule.appDatabase.taskDao().getTasks().blockingObserve()?.get(0)?.title
        assertEquals(newTitle, actualTitle)
    }

    @Test
    fun deleteTaskTest() {
        val testTask = TaskEntity(5, "Прочитать Совершенный код", false, TaskCategory.STUDY)
        databaseRule.appDatabase.taskDao().insertTask(testTask)
        val taskSize = databaseRule.appDatabase.taskDao().getTasks().blockingObserve()?.size
        assertEquals(taskSize, 1)

        databaseRule.appDatabase.taskDao().deleteTask(testTask)
        val newTaskSize = databaseRule.appDatabase.taskDao().getTasks().blockingObserve()?.size
        assertEquals(newTaskSize, 0)
    }

    @Test
    fun getTaskTest() {
        val testTask = TaskEntity(5, "Прочитать Совершенный код", false, TaskCategory.STUDY)
        databaseRule.appDatabase.taskDao().insertTask(testTask)
        val taskSize = databaseRule.appDatabase.taskDao().getTasks().blockingObserve()?.size
        assertEquals(taskSize, 1)
    }
}