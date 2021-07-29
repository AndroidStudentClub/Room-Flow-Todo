package ru.androidschool.besttodo.presentation.create

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.dialog_create_task.view.*
import ru.androidschool.besttodo.R
import ru.androidschool.besttodo.data.model.TaskEntity
import ru.androidschool.besttodo.data.model.TaskCategory

class CreateTaskDialog(
    context: Context,
    private val onMerge: (task: TaskEntity) -> Unit
) : BottomSheetDialog(context, R.style.TransparentBottomSheet) {

    override fun onCreate(savedInstanceState: Bundle?) {
        val contentView = View.inflate(context, R.layout.dialog_create_task, null)
        setContentView(contentView)

        val saveButton = contentView.findViewById<LinearLayout>(R.id.save_button)

        saveButton.setOnClickListener {
            val newTask = TaskEntity(
                title = contentView.task_name_edit_text.text.toString(),
                isCompleted = false,
                category = getCategoryFromChipId(contentView.chip_group.checkedChipId)
            )

            onMerge(newTask)
            dismiss()
        }

        super.onCreate(savedInstanceState)
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

    companion object {
        fun newInstance(
            context: Context,
            onMerge: (task: TaskEntity) -> Unit
        ) {
            val dialog = CreateTaskDialog(context, onMerge)
            dialog.show()
        }
    }
}