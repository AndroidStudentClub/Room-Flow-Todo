package ru.androidschool.besttodo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import ru.androidschool.besttodo.data.model.TaskEntity
import ru.androidschool.besttodo.data.repository.TasksRepositoryImpl
import ru.androidschool.besttodo.databinding.FragmentTasksBinding
import ru.androidschool.besttodo.presentation.create.CreateTaskFragment
import ru.androidschool.besttodo.presentation.main.TasksAdapter
import ru.androidschool.besttodo.presentation.main.TasksListViewModel
import ru.androidschool.besttodo.presentation.main.TasksListViewModelFactory

class TasksListFragment : Fragment(), CreateTaskFragment.TaskClickListener {

    private var _binding: FragmentTasksBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var placeholderContainer: ConstraintLayout
    private val taskAdapter = TasksAdapter() { taskListViewModel.updateTask(it) }

    private val taskListViewModel: TasksListViewModel by viewModels {
        TasksListViewModelFactory(
            TasksRepositoryImpl(requireContext(), Dispatchers.IO)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.fab.setOnClickListener {
            val dialog = CreateTaskFragment.newInstance()
            dialog.show(childFragmentManager, CreateTaskFragment.TAG)
        }

        taskListViewModel.getAllTasks().observe(viewLifecycleOwner, Observer<List<TaskEntity>> {
            updateResults(it)
        })
    }

    override fun onItemClick(task: TaskEntity) {
        taskListViewModel.insertTask(task)
    }

    private fun updatePlaceholder() {
        if (taskAdapter.isEmpty()) {
            binding.emptyPlaceholder.placeholder.visibility = View.VISIBLE
        } else {
            binding.emptyPlaceholder.placeholder.visibility = View.GONE
        }
    }

    private fun updateResults(tasks: List<TaskEntity>) {
        taskAdapter.setTasks(tasks)
        binding.tasks.apply {
            adapter = taskAdapter
        }
        updatePlaceholder()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}