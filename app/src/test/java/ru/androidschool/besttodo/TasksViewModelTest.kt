package ru.androidschool.besttodo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.Assert.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import ru.androidschool.besttodo.data.model.TaskEntity
import ru.androidschool.besttodo.data.model.TaskCategory
import ru.androidschool.besttodo.data.repository.TasksRepositoryImpl
import ru.androidschool.besttodo.presentation.main.TasksListViewModel

// 1
@RunWith(AndroidJUnit4::class)
class TasksViewModelTest {
    // 2
    @Mock
    private lateinit var viewModel: TasksListViewModel

    @Mock
    private lateinit var isLoadingLiveData: LiveData<Boolean>

    @Mock
    private lateinit var observer: Observer<in Boolean>

    // 3
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // 4
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = spy(
            TasksListViewModel(
                TasksRepositoryImpl(ApplicationProvider.getApplicationContext(), TestCoroutineDispatcher())
            )
        )
        isLoadingLiveData = viewModel.dataLoading
    }

    /**
     * Testing *onChanged()* method for [LiveData]
     *
     */
    @Test
    fun `Verify livedata values changes on event`() {
        assertNotNull(viewModel.getAllTasks())
        viewModel.dataLoading.observeForever(observer)
        verify(observer).onChanged(false)
        viewModel.getAllTasks()
        verify(observer).onChanged(true)
        viewModel.dataLoading.removeObserver(observer)
    }

    @Test
    fun `Assert loading values are correct fetching tasks`() {
        // 1
        var isLoading = isLoadingLiveData.value
        // 2
        assertNotNull(isLoading)
        // 3
        isLoading?.let { assertTrue(it) }
        // 4
        viewModel.getAllTasks()
        // 5
        isLoading = isLoadingLiveData.value
        assertNotNull(isLoading)
        isLoading?.let { assertFalse(it) }
    }

    @Test
    fun `Assert loading values are correct deleting task`() {
        // 1
        val testTask = TaskEntity(1, "Прочитать Совершенный код", false, TaskCategory.STUDY)
        // 2
        var isLoading = isLoadingLiveData.value
        // 3
        assertNotNull(isLoading)
        // 4
        isLoading?.let { assertTrue(it) }
        // 5
        viewModel.deleteTask(testTask)
        // 6
        isLoading = isLoadingLiveData.value
        assertNotNull(isLoading)
        isLoading?.let { assertFalse(it) }
    }

    @Test
    fun `Assert loading values are correct updating task`() {
        // 1
        val testTask = TaskEntity(1, "Прочитать Совершенный код", false, TaskCategory.STUDY)
        // 2
        var isLoading = isLoadingLiveData.value
        // 3
        assertNotNull(isLoading)
        // 4
        isLoading?.let { assertTrue(it) }
        // 5
        viewModel.updateTask(testTask)
        // 6
        isLoading = isLoadingLiveData.value
        assertNotNull(isLoading)
        isLoading?.let { assertFalse(it) }
    }
}