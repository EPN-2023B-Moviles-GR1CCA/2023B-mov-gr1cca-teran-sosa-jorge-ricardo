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
import com.example.examenibricardoteran.db.DbInmueble

class Inmueble : AppCompatActivity() {
    companion object {
        var idItemSeleccionado = 0
    }
//    var idItemSeleccionado = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inmueble)

        val tipo = findViewById<EditText>(R.id.editText_tipo)
        tipo.requestFocus()
        val direccion = findViewById<EditText>(R.id.editText_direccion)
        val antiguedad = findViewById<EditText>(R.id.editText_antiguead)
        val valor = findViewById<EditText>(R.id.editText_precio)
        val idInmobiliaria = findViewById<EditText>(R.id.editText_idinmobiliaria)

        val btnInsertar = findViewById<Button>(R.id.button_insertar)
        btnInsertar.setOnClickListener {
            val inmueble: DbInmueble = DbInmueble(
                null,
                tipo.text.toString(),
                direccion.text.toString(),
                antiguedad.text.toString(),
                valor.text.toString(),
                idInmobiliaria.text.toString().toInt(),
                this
            )
            val resultado = inmueble.insertInmueble()

            if (resultado > 0) {
                Toast.makeText(this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show()
                cleanEditText()
            } else {
                Toast.makeText(this, "ERROR AL INSERTAR REGISTRO", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun showListViewInmueble() {
        val listViewInmueble = findViewById<ListView>(R.id.listview_inmueble)
        val inmueble = DbInmueble(
            null,
            "",
            "",
            "",
            "",
            null,
            this
        )
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            inmueble.showInmueble()
        )
        listViewInmueble.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listViewInmueble)
    }

    fun cleanEditText() {
        val tipo = findViewById<EditText>(R.id.editText_tipo)
        tipo.setText("")
        val direccion = findViewById<EditText>(R.id.editText_direccion)
        direccion.setText("")
        val antiguedad = findViewById<EditText>(R.id.editText_antiguead)
        antiguedad.setText("")
        val valor = findViewById<EditText>(R.id.editText_precio)
        valor.setText("")
        val idInmobiliaria = findViewById<EditText>(R.id.editText_idinmobiliaria)
        idInmobiliaria.setText("")
        tipo.requestFocus()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        //Llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menuinmueble, menu)
        //Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar_inmueble -> {
                val inmueble = DbInmueble(
                    null,
                    "",
                    "",
                    "",
                    "",
                    null,
                    this
                )
                val lista = inmueble.showInmuebleBy(idItemSeleccionado)
                val inmuebleSeleccionado = lista.get(0)
                val intent = Intent(this, ActualizarInmueble::class.java)
                intent.putExtra("id_inmueble", inmuebleSeleccionado.getidInmueble())
                intent.putExtra("tipo_inmueble", inmuebleSeleccionado.gettipoInmueble())
                intent.putExtra("direccion_inmueble", inmuebleSeleccionado.getdireccionInmueble())
                intent.putExtra("antiguedad_inmueble", inmuebleSeleccionado.getantiguedadInmueble())
                intent.putExtra("valor_inmueble", inmuebleSeleccionado.getvalorInmueble())
                intent.putExtra("IdInmobiliaria", inmuebleSeleccionado.getidInmobiliaria())
                startActivity(intent)
                true
            }
            R.id.mi_eliminar_inmueble -> {
                abrirDialogo()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogo() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("¿Desea eliminar el registro?")
            .setPositiveButton("SI",
                DialogInterface.OnClickListener { dialog, id ->
                    val inmueble = DbInmueble(
                        null,
                        "",
                        "",
                        "",
                        "",
                        null,
                        this
                    )
                    val lista = inmueble.showInmuebleBy(idItemSeleccionado)
                    val inmuebleSeleccionado = lista.get(0)
                    val resultado = inmuebleSeleccionado.deleteInmueble()
                    if (resultado > 0) {
                        Toast.makeText(this, "REGISTRO ELIMINADO", Toast.LENGTH_LONG).show()
                        showListViewInmueble()
                    } else {
                        Toast.makeText(this, "ERROR AL ELIMINAR REGISTRO", Toast.LENGTH_LONG)
                            .show()
                    }
                })
            .setNegativeButton("NO",
                DialogInterface.OnClickListener { dialog, id ->
                    // User cancelled the dialog
                })
        builder.show()
    }

    fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}