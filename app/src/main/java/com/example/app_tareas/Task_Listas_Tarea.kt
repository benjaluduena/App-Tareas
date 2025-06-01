package com.example.app_tareas

data class Task_Listas_Tarea(
    val title: String,
    val category: String,
    val iconRes: Int,
    var isDone: Boolean,
    val preview: String,
    var seleccionada: Boolean = false
)
