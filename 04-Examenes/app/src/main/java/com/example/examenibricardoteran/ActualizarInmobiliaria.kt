package com.example.examenibricardoteran

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.examenibricardoteran.db.DbInmobiliaria
import com.google.firebase.firestore.FirebaseFirestore

class ActualizarInmobiliaria : AppCompatActivity() {

    private val dbFirestore = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_inmobiliaria)

        val idInmobiliaria = Inmobiliaria.idInmobiliariaSeleccionado
        var inmobiliaria = DbInmobiliaria(null, "", "", this)
        inmobiliaria = inmobiliaria.getInmobiliariaById(idInmobiliaria)

        var id = findViewById<EditText>(R.id.et_upd_idInm)
        id.setText(inmobiliaria.getidInmobiliaria().toString())
        var nombre = findViewById<EditText>(R.id.et_upd_nombInm)
        nombre.setText(inmobiliaria.getnombreInmobiliaria())
        var direccion = findViewById<EditText>(R.id.et_upd_direcInm)
        direccion.setText(inmobiliaria.getdireccionInmobiliaria())

        val btn_actualizarInmobiliaria = findViewById<Button>(R.id.btn_actualizarInm)
        btn_actualizarInmobiliaria.setOnClickListener {
            //Actualizar Inmobiliaria Firestone
            dbFirestore.collection("inmobiliaria").whereEqualTo("nombre", inmobiliaria.getnombreInmobiliaria())
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        dbFirestore.collection("inmobiliaria").document(document.id).set(
                            hashMapOf(
                                "nombre" to nombre.text.toString(),
                                "direccion" to direccion.text.toString()
                            )
                        )
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Error al actualizar registro", Toast.LENGTH_LONG).show()
                }

            // Inmobiliaria actualizado
            inmobiliaria.setnombreInmobiliaria(nombre.text.toString())
            inmobiliaria.setdireccionInmobiliaria(direccion.text.toString())
            val resultado = inmobiliaria.updateInmobiliaria()

            if (resultado > 0) {
                Toast.makeText(this, "REGISTRO ACTUALIZADO", Toast.LENGTH_LONG).show()
//                cleanEditText()
            } else {
                Toast.makeText(this, "ERROR AL ACTUALIZAR REGISTRO", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun cleanEditText() {
        val id = findViewById<EditText>(R.id.et_upd_idInm)
        id.setText("")
        val nombre = findViewById<EditText>(R.id.et_upd_nombInm)
        nombre.setText("")
        val direccion = findViewById<EditText>(R.id.et_upd_direcInm)
        direccion.setText("")
        id.requestFocus()
    }



}