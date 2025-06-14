package com.example.app_tareas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val tasks: MutableList<Task_Listas_Tarea>,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.txtTitle)
        val category: TextView = itemView.findViewById(R.id.txtCategory)
        val preview: TextView = itemView.findViewById(R.id.txtPreview)
        val icon: ImageView = itemView.findViewById(R.id.imgIcon)
        val checkbox: CheckBox = itemView.findViewById(R.id.chkDone)
        val deleteIcon: ImageView = itemView.findViewById(R.id.imgDelete) // nuevo
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
        holder.preview.text = task.preview
        holder.icon.setImageResource(task.iconRes)
        holder.checkbox.isChecked = task.isDone
        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
            task.isDone = isChecked
        }

        // Mostrar u ocultar el ícono de basura
        if (task.seleccionada) {
            holder.deleteIcon.apply {
                visibility = View.VISIBLE
                alpha = 0f
                scaleX = 0.7f
                scaleY = 0.7f
                animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(250)
                    .start()
            }
        } else {
            holder.deleteIcon.visibility = View.GONE
        }


        // Evento click sobre papelera
        holder.deleteIcon.setOnClickListener {
            onDeleteClick(position)
        }

        // Mantener presionado por 5 segundos para mostrar papelera
        holder.itemView.setOnLongClickListener {
            task.seleccionada = !task.seleccionada
            notifyItemChanged(position)
            true
        }
    }

    override fun getItemCount(): Int = tasks.size
}

