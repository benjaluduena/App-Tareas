package com.example.app_tareas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val tasks: List<Task_Listas_Tarea>) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.txtTitle)
        val category: TextView = itemView.findViewById(R.id.txtCategory)
        val preview: TextView = itemView.findViewById(R.id.txtPreview) // NUEVO
        val icon: ImageView = itemView.findViewById(R.id.imgIcon)
        val checkbox: CheckBox = itemView.findViewById(R.id.chkDone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task_listas, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.title.text = task.title
        holder.category.text = task.category
        holder.preview.text = task.preview // NUEVO
        holder.icon.setImageResource(task.iconRes)
        holder.checkbox.isChecked = task.isDone

        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
            task.isDone = isChecked
        }
    }

    override fun getItemCount(): Int = tasks.size
}
