package com.example.app_tareas

import android.os.Bundle
import android.widget.ImageView
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
        findViewById<BottomNavigationView>(R.id.bottomNav)
            .setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_tareas        -> { /*…*/ true }
                    R.id.nav_calendario    -> { /*…*/ true }
                    R.id.nav_configuracion -> { /*…*/ true }
                    else                   -> false
                }
            }
    }
}
