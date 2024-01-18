package ru.androidschool.besttodo.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.androidschool.besttodo.data.database.TaskDatabase
import ru.androidschool.besttodo.data.model.TaskEntity
import ru.androidschool.besttodo.domain.repository.TaskRepository

class TasksListViewModel(private val repository: TaskRepository) :
    ViewModel() {

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    init {
        for (task in TaskDatabase.PREPOPULATE_DATA) {
            viewModelScope.launch {
                repository.insert(task)
            }
        }
    }

    fun insertTask(task: TaskEntity) {
        showProgress()
        viewModelScope.launch {
            repository.insert(task)
        }
        hideProgress()
    }

    fun updateTask(task: TaskEntity) {
        showProgress()
        viewModelScope.launch {
            repository.update(task)
        }
        hideProgress()
    }

    fun deleteTask(task: TaskEntity) {
        showProgress()
        viewModelScope.launch {
            repository.delete(task)
        }
        hideProgress()
    }

    fun getAllTasks(): LiveData<List<TaskEntity>> {
        val tasks: LiveData<List<TaskEntity>>?
        _dataLoading.value = true
        tasks = repository.getAllTasks().asLiveData()
        _dataLoading.value = false
        return tasks
    }

    private fun showProgress() {
        _dataLoading.value = true
    }

    private fun hideProgress() {
        _dataLoading.value = false
    }

}