package com.example.app_tareas

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast

class BienvenidaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenida)

        val btnContinuar = findViewById<Button>(R.id.btnContinuar)
        btnContinuar.setOnClickListener {
            // Por ahora mostramos un mensaje y cerramos la pantalla
            Toast.makeText(this, "Continuar presionado", Toast.LENGTH_SHORT).show()
            finish()

            // Cuando tengas la pantalla siguiente, descomentá esto y reemplazá con el nombre correcto
            // startActivity(Intent(this, NombreDeTuActividad::class.java))
        }
    }
}
