package ru.androidschool.besttodo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.androidschool.besttodo.data.database.TaskDatabase
import ru.androidschool.besttodo.data.model.Subtask
import ru.androidschool.besttodo.data.model.TaskCategory
import ru.androidschool.besttodo.data.model.TaskEntity

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class OneToManyTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val databaseRule = DatabaseRule()

    private lateinit var database: TaskDatabase

    @Before
    fun initDb() {
        // Using an in-memory database so that the information stored here disappears when the
        // process is killed.
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            TaskDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun relations() {

        val parentTask1 = TaskEntity(
            1,
            "Read book about Unit Tests",
            false,
            category = TaskCategory.STUDY
        )

        val subtask1 = Subtask(
            subtaskId = 1,
            title = "Another title for subtask",
            description = "Another description",
            isCompleted = false,
            parentTaskId = parentTask1.id
        )

        val subtask2 = Subtask(
            subtaskId = 2,
            title = "Some title for subtask",
            description = "Some description",
            isCompleted = false,
            parentTaskId = parentTask1.id
        )

        val underTest = database.taskDao()

        runTest {
            val tasks = underTest.getTasksWithSubtasks()
            assertEquals(true, tasks.isEmpty())

            underTest.insertTask(parentTask1)
            underTest.insertSubTask(subtask1)
            underTest.insertSubTask(subtask2)

            assertEquals(false, underTest.getTasksWithSubtasks().isEmpty())

            val all = underTest.getTasksWithSubtasks()

            assertEquals(1, all.size)
            assertEquals(2, all[0].subtasks.size)
            assertEquals(subtask1, all[0].subtasks[0])
            assertEquals(subtask2, all[0].subtasks[1])

            val loaded = underTest.getTasksById(parentTask1.id)

            assertEquals(loaded[0].subtasks[0], subtask1)
            assertEquals(loaded[0].subtasks[1], subtask2)
        }
    }
}