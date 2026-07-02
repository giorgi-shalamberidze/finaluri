package com.example.finaluri.ui

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.finaluri.R
import com.example.finaluri.databinding.DialogAddTaskBinding
import com.example.finaluri.viewmodel.TaskViewModel

class AddTaskDialogFragment : DialogFragment() {
    private var _binding: DialogAddTaskBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TaskViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogAddTaskBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(R.string.add_task)
            .setView(binding.root)
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.save, null)
            .create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                saveTask()
            }
        }

        return dialog
    }

    private fun saveTask() = with(binding) {
        val title = taskTitleEditText.text.toString()
        val description = taskDescriptionEditText.text.toString()

        if (viewModel.addTask(title, description)) {
            dismiss()
        } else {
            taskTitleEditText.error = getString(R.string.title_required)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
