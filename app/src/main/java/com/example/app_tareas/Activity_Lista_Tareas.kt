package com.example.app_tareas

import com.example.app_tareas.TaskAdapter

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import com.google.android.material.floatingactionbutton.FloatingActionButton



class Activity_Lista_Tareas : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TaskAdapter
    private val taskList = mutableListOf<Task_Listas_Tarea>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_de_tareas) // ← tu layout real

        recyclerView = findViewById(R.id.recyclerNotes)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Lista de ejemplo
        taskList.add(
            Task_Listas_Tarea(
                title = "Learn new things",
                category = "Study - 27 May",
                preview = "Start with Kotlin and build a simple app to test MVVM pattern.alsfdglasdfdglagfsdlagfdlagfljsdfjaflasdljfgalsjsdfgsldfjaljfglsajdfglajfsg", // Ahora va el String
                iconRes = R.drawable.baseline_menu_book_24,
                isDone = false
            )
        )

        taskList.add(
            Task_Listas_Tarea(
                title = "Design things",
                category = "Work - 28 May",
                preview = "Design a modern UI for the task app and implement dark mode.",
                iconRes = R.drawable.baseline_design_services_24,
                isDone = true
            )
        )

        taskList.add(
            Task_Listas_Tarea(
                title = "Share my work",
                category = "Social - 29 May",
                preview = "Post the new project on GitHub and share on social media.",
                iconRes = R.drawable.baseline_share_24,
                isDone = false
            )
        )
        adapter = TaskAdapter(taskList)
        recyclerView.adapter = adapter


        val botonMasInfo = findViewById<FloatingActionButton>(R.id.botonagregar)
        botonMasInfo.setOnClickListener {
            val intent = Intent(this, ActivityNuevaTarea::class.java)
            startActivity(intent)
        }
    }
}
