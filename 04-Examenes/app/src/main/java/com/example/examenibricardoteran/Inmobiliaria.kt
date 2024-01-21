package com.example.examenibricardoteran

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.examenibricardoteran.db.DbInmobiliaria

class Inmobiliaria : AppCompatActivity() {

    companion object{
        var idInmobiliariaSeleccionado = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inmobiliaria)
        showListViewInmobiliaria()

        val nombre = findViewById<EditText>(R.id.editText_nombreInm)
        nombre.requestFocus()
        val direccion = findViewById<EditText>(R.id.editText_direccionInm)

        val btnInsertar = findViewById<Button>(R.id.btn_insertarInm)
        btnInsertar.setOnClickListener {
            val inmobiliaria = DbInmobiliaria(
                null,
                nombre.text.toString(),
                direccion.text.toString(),
                this
            )
            val resultado = inmobiliaria.insertInmobiliaria()

            if (resultado > 0) {
                Toast.makeText(this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show()
                cleanEditText()
                showListViewInmobiliaria()
            } else {
                Toast.makeText(this, "ERROR EN INSERTAR REGISTRO", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun cleanEditText() {
        val nombre = findViewById<EditText>(R.id.editText_nombreInm)
        nombre.setText("")
        val direccion = findViewById<EditText>(R.id.editText_direccionInm)
        direccion.setText("")
        nombre.requestFocus()
    }

    fun showListViewInmobiliaria() {
        val listViewInmobiliaria = findViewById<ListView>(R.id.listview_inmobiliaria)
        val inmobiliaria = DbInmobiliaria(null, "", "", this)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            inmobiliaria.showInmobiliaria()
        )
        listViewInmobiliaria.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listViewInmobiliaria)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        //llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menuinmobiliaria, menu)
        //Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idInmobiliariaSeleccionado = id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar_inm -> {
                irActividad(ActualizarInmobiliaria::class.java)
                return true
            }
            R.id.mi_eliminar_inm -> {
                abrirDialogo()
                return true
            }
            R.id.mi_inmuebles -> {
                irActividad(VerInmueble::class.java)
                return true
            }
            else -> super.onContextItemSelected(item)
            }
        }

    fun abrirDialogo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Estás seguro de eliminar?")
        builder.setPositiveButton(
            "Si",
            DialogInterface.OnClickListener { dialog, which ->
                val inmobiliaria = DbInmobiliaria(null, "", "", this)
                val resultado = inmobiliaria.deleteInmobiliaria(idInmobiliariaSeleccionado)
                if (resultado > 0) {
                    Toast.makeText(this, "REGISTRO ELIMINADO", Toast.LENGTH_LONG).show()
                    showListViewInmobiliaria()
                } else {
                    Toast.makeText(this, "ERROR AL ELIMINAR REGISTRO", Toast.LENGTH_LONG).show()
                }
            }
        )
        builder.setNegativeButton(
            "No",
            null
        )

        val dialogo = builder.create()
        dialogo.show()
    }

    fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

}

