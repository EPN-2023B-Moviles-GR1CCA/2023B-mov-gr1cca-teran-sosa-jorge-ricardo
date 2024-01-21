package com.example.examenibricardoteran

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.examenibricardoteran.db.DbHelper

class MainActivity : AppCompatActivity() {

    private lateinit var btnCrearBD: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCrearBD = findViewById(R.id.btnCrearBD) // Asigna el botón desde el layout

        btnCrearBD.setOnClickListener {
            // Cuando se hace clic en el botón, se crea la base de datos
            crearBaseDeDatos()
        }
    }

    private fun crearBaseDeDatos() {
        val dbHelper: DbHelper = DbHelper(this)
        val db: SQLiteDatabase = dbHelper.writableDatabase
        if (db != null) {
            // La base de datos se creó con éxito
            Toast.makeText(this, "BASE DE DATOS CREADA", Toast.LENGTH_LONG).show()

            // Luego, navegamos a la actividad Inmobiliaria
            irActividad(Inmobiliaria::class.java)
        } else {
            Toast.makeText(this, "ERROR AL CREAR LA BD", Toast.LENGTH_LONG).show()
        }
    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

}