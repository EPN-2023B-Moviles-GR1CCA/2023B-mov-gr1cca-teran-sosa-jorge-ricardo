import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity


class UpdateInmueblesActivity : ComponentActivity() {
    private lateinit var spinnerInmuebles: Spinner
    private lateinit var editTextDireccion: EditText
    private lateinit var editTextPrecio: EditText
    private lateinit var editTextAntiguedad: EditText
    private lateinit var botonActualizar: Button

    private lateinit var crudService: CRUDService
    private var inmuebles: List<Inmueble> = listOf()
    private var inmuebleSeleccionado: Inmueble? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inmuebles_update_activity)

        spinnerInmuebles = findViewById(R.id.spinnerInmuebles)
        editTextDireccion = findViewById(R.id.editTextDireccion)
        editTextPrecio = findViewById(R.id.editTextPrecio)
        editTextAntiguedad = findViewById(R.id.editTextAntiguedad)
        botonActualizar = findViewById(R.id.botonActualizar)

        crudService = CRUDService(this)

        cargarInmuebles()
        configurarSpinnerInmuebles()
    }

    private fun cargarInmuebles() {
        inmuebles = crudService.leerInmuebles()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, inmuebles.map { it.direccion })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInmuebles.adapter = adapter
    }

    private fun configurarSpinnerInmuebles() {
        spinnerInmuebles.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                inmuebleSeleccionado = inmuebles[position]
                cargarDatosInmueble(inmuebleSeleccionado!!)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                inmuebleSeleccionado = null
            }
        }
    }

    private fun cargarDatosInmueble(inmueble: Inmueble) {
        editTextDireccion.setText(inmueble.direccion)
        editTextPrecio.setText(inmueble.precio.toString())
        editTextAntiguedad.setText(inmueble.antiguedad.toString())
    }

    fun onActualizarInmuebleClick(view: View) {
        inmuebleSeleccionado?.let {
            val direccion = editTextDireccion.text.toString()
            val precio = editTextPrecio.text.toString().toDoubleOrNull() ?: 0.0
            val antiguedad = editTextAntiguedad.text.toString().toIntOrNull() ?: 0

            val inmuebleActualizado = Inmueble(precio, direccion, antiguedad, it.inmobiliaria) // Asumiendo que 'inmobiliaria' es un campo existente en Inmueble
            try {
                crudService.actualizarInmueble(it.direccion, inmuebleActualizado) // Asumiendo que actualizamos por dirección
                Toast.makeText(this, "Inmueble actualizado con éxito", Toast.LENGTH_SHORT).show()
                cargarInmuebles()
            } catch (e: Exception) {
                Toast.makeText(this, "Error al actualizar el inmueble", Toast.LENGTH_SHORT).show()
            }
        } ?: Toast.makeText(this, "Por favor, seleccione un inmueble", Toast.LENGTH_SHORT).show()
    }
}
