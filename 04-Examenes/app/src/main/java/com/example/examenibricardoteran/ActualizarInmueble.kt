package com.example.examenibricardoteran

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.examenibricardoteran.db.DbInmueble

class ActualizarInmueble : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_inmueble)

        val idInmueble = Inmueble.idItemSeleccionado
        var inmueble = DbInmueble(null, "", "", "", "",0, this)
        inmueble = inmueble.getInmuebleById(idInmueble)

        var id = findViewById<EditText>(R.id.et_upd_idInmueble)
        id.setText(inmueble.getidInmueble().toString())
        var tipo = findViewById<EditText>(R.id.et_upd_tipo)
        tipo.setText(inmueble.gettipoInmueble())
        var direccion = findViewById<EditText>(R.id.et_upd_direccion)
        direccion.setText(inmueble.getdireccionInmueble())
        var antiguedad = findViewById<EditText>(R.id.et_upd_antiguedad)
        antiguedad.setText(inmueble.getantiguedadInmueble())
        var valor = findViewById<EditText>(R.id.et_upd_precio)
        valor.setText(inmueble.getvalorInmueble().toString())
        var idInmobiliaria = findViewById<EditText>(R.id.et_upd_idInmobiliaria)
        idInmobiliaria.setText(inmueble.getidInmobiliaria().toString())

        val btn_actualizarInmueble = findViewById<Button>(R.id.btn_actualizarInmueble)
        btn_actualizarInmueble.setOnClickListener {
            // Inmueble actualizado
            inmueble.settipoInmueble(tipo.text.toString())
            inmueble.setdireccionInmueble(direccion.text.toString())
            inmueble.setantiguedadInmueble(antiguedad.text.toString())
            inmueble.setvalorInmueble(valor.text.toString())
            inmueble.setidInmobiliaria(idInmobiliaria.text.toString().toInt())
            val resultado = inmueble.updateInmueble()

            if (resultado > 0) {
                Toast.makeText(this, "REGISTRO ACTUALIZADO", Toast.LENGTH_LONG).show()
                cleanEditText()
            } else {
                Toast.makeText(this, "ERROR AL ACTUALIZAR REGISTRO", Toast.LENGTH_LONG).show()
            }
        }
    }
    fun cleanEditText() {
        var id = findViewById<EditText>(R.id.et_upd_idInmueble)
        id.setText("")
        var tipo = findViewById<EditText>(R.id.et_upd_tipo)
        tipo.setText("")
        var direccion = findViewById<EditText>(R.id.et_upd_direccion)
        direccion.setText("")
        var antiguedad = findViewById<EditText>(R.id.et_upd_antiguedad)
        antiguedad.setText("")
        var valor = findViewById<EditText>(R.id.et_upd_precio)
        valor.setText("")
        var idInmobiliaria = findViewById<EditText>(R.id.et_upd_idInmobiliaria)
        idInmobiliaria.setText("")
    }
}