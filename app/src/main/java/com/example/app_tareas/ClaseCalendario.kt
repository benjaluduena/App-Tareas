package com.example.app_tareas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.example.app_tareas.databinding.CalendarioBinding
import android.content.Intent
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomnavigation.BottomNavigationView


class ClaseCalendario : AppCompatActivity() {

    private lateinit var binding: CalendarioBinding
    private lateinit var today: CalendarDay
    private var selectedExtraDate: CalendarDay? = null  // Guardamos el día adicional seleccionado

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CalendarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val calendarView = binding.calendarView
        today = CalendarDay.today()

        // Selección múltiple (pero controlada por nosotros)
        calendarView.selectionMode = MaterialCalendarView.SELECTION_MODE_MULTIPLE

        // Seleccionamos y decoramos el día actual
        calendarView.setDateSelected(today, true)
        calendarView.addDecorator(TodayDecorator(today))

        // Listener de selección
        calendarView.setOnDateChangedListener { widget, date, selected ->
            if (date == today && !selected) {
                // Prevenir deselección del día actual
                widget.setDateSelected(today, true)
                return@setOnDateChangedListener
            }

            if (selected) {
                // Si ya hay un día adicional seleccionado, lo deseleccionamos
                selectedExtraDate?.let { previous ->
                    widget.setDateSelected(previous, false)
                }

                // Guardamos el nuevo seleccionado
                selectedExtraDate = if (date != today) date else null
            } else {
                // Si el usuario deselecciona el día adicional
                if (date == selectedExtraDate) {
                    selectedExtraDate = null
                }
            }
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_tareas -> {
                    // Abrir pantalla de inicio
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_calendario -> {
                    // Abrir pantalla de calendario
                    val intent = Intent(this, ClaseCalendario::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_listas -> {
                    // Abrir pantalla de listas
                    val intent = Intent(this, Activity_Lista_Tareas::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }



    }

}
