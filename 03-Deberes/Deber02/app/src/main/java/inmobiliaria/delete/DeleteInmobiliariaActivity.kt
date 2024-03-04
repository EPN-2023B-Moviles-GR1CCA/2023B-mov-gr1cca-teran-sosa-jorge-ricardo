
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity

class DeleteInmobiliariaActivity : ComponentActivity() {
    private lateinit var spinnerInmobiliarias: Spinner
    private lateinit var botonEliminar: Button
    private lateinit var crudService: CRUDService
    private var inmobiliarias: List<Inmobiliaria> = listOf()
    private var inmobiliariaSeleccionada: Inmobiliaria? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inmobiliarias_delete_activity)

        spinnerInmobiliarias = findViewById(R.id.spinnerInmobiliarias)
        botonEliminar = findViewById(R.id.botonEliminar)
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
                inmobiliariaSeleccionada = inmobiliarias[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                inmobiliariaSeleccionada = null
            }
        }
    }

    fun onEliminarInmobiliariaClick(view: View) {
        inmobiliariaSeleccionada?.let {
            try {
                crudService.eliminarInmobiliaria(it.nombre)
                Toast.makeText(this, "Inmobiliaria eliminada con éxito", Toast.LENGTH_SHORT).show()
                cargarInmobiliarias() // Recargar la lista de inmobiliarias después de la eliminación
            } catch (e: Exception) {
                Toast.makeText(this, "Error al eliminar la inmobiliaria", Toast.LENGTH_SHORT).show()
            }
        } ?: Toast.makeText(this, "Por favor, seleccione una inmobiliaria", Toast.LENGTH_SHORT).show()
    }
}
