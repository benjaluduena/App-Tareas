package com.example.app_tareas

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Activity_Lista_Tareas : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TaskAdapter
    private lateinit var searchView: SearchView
    private val taskList = mutableListOf<Task_Listas_Tarea>()
    private val fullTaskList = mutableListOf<Task_Listas_Tarea>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_de_tareas)

        recyclerView = findViewById(R.id.recyclerNotes)
        recyclerView.layoutManager = LinearLayoutManager(this)

        taskList.addAll(loadTasks())
        fullTaskList.addAll(taskList)

        val tarea = intent.getStringExtra("tarea")
        val fecha = intent.getStringExtra("fecha")
        val hora = intent.getStringExtra("hora")
        val categoria = intent.getStringExtra("categoria")

        if (!tarea.isNullOrEmpty() && !categoria.isNullOrEmpty()) {
            val icono = when (categoria.uppercase()) {
                "DESIGN" -> R.drawable.baseline_design_services_24
                "FRONTEND" -> R.drawable.baseline_app_shortcut_24
                "BACKEND" -> R.drawable.baseline_app_settings_alt_24
                "MARKETING" -> R.drawable.baseline_insert_chart_outlined_24
                else -> R.drawable.baseline_insert_chart_outlined_24
            }

            val nuevaTarea = Task_Listas_Tarea(
                tarea,
                "$categoria - $fecha",
                icono,
                false,
                "Horario: $hora"
            )

            taskList.add(nuevaTarea)
            fullTaskList.add(nuevaTarea)
            saveTasks()
        }

        adapter = TaskAdapter(taskList) { posicion ->
            confirmarYEliminarTarea(posicion)
        }
        recyclerView.adapter = adapter

        val botonMasInfo = findViewById<FloatingActionButton>(R.id.botonagregar)
        botonMasInfo.setOnClickListener {
            val intent = Intent(this, ActivityNuevaTarea::class.java)
            startActivity(intent)
        }

// Configuración del SearchView
        searchView = findViewById(R.id.searchBar)

        val searchEditText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchEditText?.setTextColor(Color.WHITE)
        searchEditText?.setHintTextColor(Color.LTGRAY)
        searchEditText?.setBackgroundColor(Color.TRANSPARENT)

        val searchIcon = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
        searchIcon?.setColorFilter(Color.WHITE)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                filterTasks(newText.orEmpty())
                return true
            }
        })}


        private fun saveTasks() {
        val sharedPreferences = getSharedPreferences("tareas", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(fullTaskList)
        editor.putString("task_list", json)
        editor.apply()
    }

    private fun loadTasks(): MutableList<Task_Listas_Tarea> {
        val sharedPreferences = getSharedPreferences("tareas", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("task_list", null)
        val type = object : TypeToken<MutableList<Task_Listas_Tarea>>() {}.type
        return if (json != null) gson.fromJson(json, type) else mutableListOf()
    }

    private fun confirmarYEliminarTarea(posicion: Int) {
        if (posicion !in taskList.indices) return

        MaterialAlertDialogBuilder(this)
            .setTitle("¿Eliminar tarea?")
            .setMessage("Esta acción no se puede deshacer. ¿Estás seguro?")
            .setPositiveButton("Eliminar") { _, _ ->
                val tareaEliminada = taskList[posicion]
                recyclerView.findViewHolderForAdapterPosition(posicion)?.itemView?.animate()
                    ?.alpha(0f)
                    ?.setDuration(300)
                    ?.withEndAction {
                        val indexInFull = fullTaskList.indexOf(tareaEliminada)
                        taskList.removeAt(posicion)
                        fullTaskList.removeAt(indexInFull)
                        adapter.notifyItemRemoved(posicion)
                        saveTasks()

                        Snackbar.make(recyclerView, "Tarea eliminada", Snackbar.LENGTH_LONG)
                            .setAction("DESHACER") {
                                fullTaskList.add(indexInFull, tareaEliminada)
                                filterTasks(searchView.query.toString())
                                saveTasks()
                            }
                            .show()
                    }
                    ?.start()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun filterTasks(query: String) {
        val filteredList = fullTaskList.filter {
            it.title.contains(query, ignoreCase = true) ||
                    it.category.contains(query, ignoreCase = true)
        }

        taskList.clear()
        taskList.addAll(filteredList)
        adapter.notifyDataSetChanged()
    }
}
