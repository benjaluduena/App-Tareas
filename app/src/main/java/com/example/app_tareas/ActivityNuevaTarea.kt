package com.example.app_tareas

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class ActivityNuevaTarea : AppCompatActivity() {

    lateinit var tvDate: TextView
    lateinit var tvTime: TextView

    private val calendar = Calendar.getInstance()
    private var startHour = 0
    private var startMinute = 0
    private var endHour = 0
    private var endMinute = 0

    private var selectedCategory: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nueva_tarea)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvDate = findViewById(R.id.tvDate)
        tvTime = findViewById(R.id.tvTime)

        // Selector de fecha
        tvDate.setOnClickListener {
            val datePicker = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    val formato = SimpleDateFormat("EEEE dd, MMMM", Locale.getDefault())
                    tvDate.text = formato.format(calendar.time)
                    
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)

            )
            // ✅ Bloquear fechas anteriores a hoy
            datePicker.datePicker.minDate = System.currentTimeMillis() - 1000
            datePicker.show()
        }

        // Selector de horas
        tvTime.setOnClickListener {
            val timePickerStart = TimePickerDialog(
                this,
                { _, hourOfDay, minute ->
                    startHour = hourOfDay
                    startMinute = minute

                    val timePickerEnd = TimePickerDialog(
                        this,
                        { _, hourEnd, minuteEnd ->
                            endHour = hourEnd
                            endMinute = minuteEnd

                            // Validación: fin debe ser después del inicio
                            if (endHour < startHour || (endHour == startHour && endMinute <= startMinute)) {
                                Toast.makeText(
                                    this,
                                    "La hora de fin debe ser posterior a la de inicio",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                tvTime.text = "${formatTime(startHour, startMinute)} - ${formatTime(endHour, endMinute)}"
                            }
                        },
                        startHour, // desde la hora de inicio
                        startMinute,
                        false
                    )
                    timePickerEnd.setTitle("Seleccionar hora de fin")
                    timePickerEnd.show()

                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false
            )
            timePickerStart.setTitle("Seleccionar hora de inicio")
            timePickerStart.show()
        }




        val btnDesign = findViewById<Button>(R.id.btnDesign)
        val btnFrontend = findViewById<Button>(R.id.btnFrontend)
        val btnBackend = findViewById<Button>(R.id.btnBackend)
        val btnMarketing = findViewById<Button>(R.id.btnMarketing)

        val categoryButtons = listOf(btnDesign, btnFrontend, btnBackend, btnMarketing)

        categoryButtons.forEach { button ->
            button.setOnClickListener {
                // Restaurar color anterior
                selectedCategory?.backgroundTintList = getColorStateList(R.color.categoria_normal)
                selectedCategory?.setTextColor(getColor(R.color.text_normal))

                // Marcar el nuevo
                button.backgroundTintList = getColorStateList(R.color.categoria_seleccionada)
                button.setTextColor(getColor(R.color.text_seleccionado))

                // Guardar el botón seleccionado
                selectedCategory = button
            }
        }



    }

        private fun formatTime(hour: Int, minute: Int): String {
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            return SimpleDateFormat("h:mm a", Locale.getDefault()).format(cal.time)
        }


}