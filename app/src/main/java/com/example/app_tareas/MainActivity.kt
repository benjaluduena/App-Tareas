package com.example.app_tareas

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val PREFS = "settings"
    private val KEY_DARK = "dark_mode"

    override fun onCreate(savedInstanceState: Bundle?) {
        // 1) Aplicar tema guardado ANTES de inflar
        val prefs = getSharedPreferences(PREFS, MODE_PRIVATE)
        var isDark = prefs.getBoolean(KEY_DARK, false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDark) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        // 2) Listener sobre el foco (ivDarkMode) para alternar tema
        findViewById<ImageView>(R.id.ivDarkMode).setOnClickListener {
            isDark = !isDark
            AppCompatDelegate.setDefaultNightMode(
                if (isDark) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
            prefs.edit().putBoolean(KEY_DARK, isDark).apply()
        }

        // 3) BottomNavigationView
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_tareas -> {
                    // Abrir pantalla de lista de tareas
                    val intent = Intent(this, Activity_Lista_Tareas::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_calendario -> {
                    // Abrir pantalla de calendario
                    val intent = Intent(this, ClaseCalendario::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_configuracion -> {
                    // Abrir pantalla de configuración (si existe)
                    // por ejemplo:
                    // val intent = Intent(this, ActivityConfiguracion::class.java)
                    // startActivity(intent)
                    true
                }
                else -> false
            }
        }

        // 4) Floating action (o botón) para agregar nueva tarea
        findViewById<LinearLayout>(R.id.agregarTarea).setOnClickListener {
            val intent = Intent(this, ActivityNuevaTarea::class.java)
            startActivity(intent)
        }
    }
}
