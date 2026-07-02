package com.example.finaluri.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finaluri.data.TaskEntity
import com.example.finaluri.databinding.ItemTaskBinding

class TaskAdapter(
    private val onDeleteTask: (TaskEntity) -> Unit
) : ListAdapter<TaskEntity, TaskAdapter.TaskViewHolder>(TaskDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(
        private val binding: ItemTaskBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(task: TaskEntity) = with(binding) {
            taskTitleTextView.text = task.title
            taskDescriptionTextView.text = task.description
            taskDescriptionTextView.visibility =
                if (task.description.isBlank()) android.view.View.GONE else android.view.View.VISIBLE

            deleteTaskButton.setOnClickListener {
                onDeleteTask(task)
            }
            root.setOnLongClickListener {
                onDeleteTask(task)
                true
            }
        }
    }

    private object TaskDiffCallback : DiffUtil.ItemCallback<TaskEntity>() {
        override fun areItemsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
            return oldItem == newItem
        }
    }
}
