package ru.androidschool.besttodo.presentation.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.androidschool.besttodo.R
import ru.androidschool.besttodo.data.model.TaskCategory
import ru.androidschool.besttodo.data.model.TaskEntity
import ru.androidschool.besttodo.databinding.DialogCreateTaskBinding
import ru.androidschool.besttodo.presentation.main.TasksListViewModel

class CreateTaskFragment : BottomSheetDialogFragment() {

    private var _binding: DialogCreateTaskBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: TasksListViewModel by viewModels({requireParentFragment()})

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogCreateTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            val newTask = TaskEntity(
                title = binding.taskNameEditText.text.toString(),
                isCompleted = false,
                category = getCategoryFromChipId(binding.chipGroup.checkedChipId)
            )

            viewModel.insertTask(newTask)
            dismiss()
        }
    }

    private fun getCategoryFromChipId(chipId: Int): TaskCategory {
        return when (chipId) {
            R.id.shopping_ship -> TaskCategory.SHOP
            R.id.work_chip -> TaskCategory.WORK
            R.id.fitness_chip -> TaskCategory.FITNESS
            R.id.study_chip -> TaskCategory.STUDY
            R.id.personal_chip -> TaskCategory.PERSONAL
            else -> TaskCategory.PERSONAL
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "ActionBottomDialog"
        fun newInstance(): CreateTaskFragment = CreateTaskFragment()
    }
}