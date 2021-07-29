package ru.androidschool.besttodo.presentation.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.androidschool.besttodo.domain.repository.TaskRepository

class TasksListViewModelFactory(
    private val repository: TaskRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TasksListViewModel(repository) as T
    }
}