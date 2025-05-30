package com.example.app_tareas


data class Task_Listas_Tarea(
    val title: String,
    val category: String,
    val preview: String,       // NUEVO CAMPO para mostrar una vista previa del contenido
    val iconRes: Int,
    var isDone: Boolean
)
