import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity

class AddInmuebleActivity : ComponentActivity() {
    private lateinit var spinnerInmobiliarias: Spinner
    private lateinit var editTextDireccionInmueble: EditText
    private lateinit var editTextPrecio: EditText
    private lateinit var editTextAntiguedad: EditText
    private lateinit var botonAgregar: Button

    private lateinit var crudService: CRUDService
    private var inmobiliarias: List<Inmobiliaria> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inmobiliarias_create_activity)

        spinnerInmobiliarias = findViewById(R.id.spinnerInmobiliarias)
        editTextDireccionInmueble = findViewById(R.id.editTextDireccionInmueble)
        editTextPrecio = findViewById(R.id.editTextPrecio)
        editTextAntiguedad = findViewById(R.id.editTextAntiguedad)
        botonAgregar = findViewById(R.id.buttonAgregarInmueble)

        crudService = CRUDService(this)

        cargarInmobiliarias()
        configurarSpinnerInmobiliarias()
    }

    private fun cargarInmobiliarias() {
        inmobiliarias = crudService.leerInmobiliarias()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, inmobiliarias.map { it.nombre })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInmobiliarias.adapter = adapter
    }

    private fun configurarSpinnerInmobiliarias() {
        spinnerInmobiliarias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    fun onAgregarInmuebleClick(view: View) {
        val inmobiliariaSeleccionada = inmobiliarias[spinnerInmobiliarias.selectedItemPosition]
        val direccion = editTextDireccionInmueble.text.toString()
        val precio = editTextPrecio.text.toString().toDoubleOrNull() ?: 0.0
        val antiguedad = editTextAntiguedad.text.toString().toIntOrNull() ?: 0

        val inmueble = Inmueble(precio, direccion, antiguedad, inmobiliariaSeleccionada.nombre)
        inmobiliariaSeleccionada.agregarInmueble(inmueble)
        crudService.actualizarInmobiliaria(inmobiliariaSeleccionada.nombre, inmobiliariaSeleccionada)
        crudService.addInmueble(inmueble)

        Toast.makeText(this, "Inmueble agregado a la inmobiliaria ${inmobiliariaSeleccionada.nombre}", Toast.LENGTH_SHORT).show()
    }
}
