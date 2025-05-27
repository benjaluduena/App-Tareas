package com.example.app_tareas



import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.app_tareas.R.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        findViewById<BottomNavigationView>(id.bottomNav)
            .setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    id.nav_tasks      -> { /* mostrar pantalla de tareas */ true }
                    id.nav_calendar   -> { /* mostrar calendario */ true }
                    id.nav_settings   -> { /* mostrar ajustes */ true }
                    else                -> false
                }
            }
    }
}
