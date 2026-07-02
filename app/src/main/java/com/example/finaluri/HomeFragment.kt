package com.example.finaluri

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finaluri.databinding.FragmentHomeBinding
import com.example.finaluri.ui.AddTaskDialogFragment
import com.example.finaluri.ui.TaskAdapter
import com.example.finaluri.viewmodel.TaskViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskAdapter: TaskAdapter
    private lateinit var viewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]
        setupTaskList()
        setupActions()
        observeTasks()
    }

    private fun setupTaskList() = with(binding) {
        taskAdapter = TaskAdapter { task ->
            viewModel.deleteTask(task)
        }

        tasksRecyclerView.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            )
        }
    }

    private fun setupActions() = with(binding) {
        addTaskFab.setOnClickListener {
            AddTaskDialogFragment().show(parentFragmentManager, AddTaskDialogFragment::class.java.name)
        }
    }

    private fun observeTasks() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.tasks.collect { tasks ->
                    taskAdapter.submitList(tasks)
                    binding.emptyTasksTextView.visibility =
                        if (tasks.isEmpty()) View.VISIBLE else View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        binding.tasksRecyclerView.adapter = null
        _binding = null
        super.onDestroyView()
    }
}
