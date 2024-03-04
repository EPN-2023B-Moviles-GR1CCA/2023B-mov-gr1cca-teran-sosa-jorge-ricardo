import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import java.time.LocalDate

class AddInmuebleActivity : ComponentActivity() {
    private lateinit var crudService: CRUDService

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inmueble_create_activity)

        val editTextDireccion = findViewById<EditText>(R.id.editTextDireccion)
        val editTextPrecio = findViewById<EditText>(R.id.editTextPrecio)
        val editTextAntiguedad = findViewById<EditText>(R.id.editTextAntiguedad)
        val buttonAgregar = findViewById<Button>(R.id.buttonAgregarInmueble)

        buttonAgregar.setOnClickListener {
            val direccion = editTextDireccion.text.toString()
            val precio = editTextPrecio.text.toString().toDoubleOrNull() ?: 0.0
            val antiguedad = editTextAntiguedad.text.toString().toIntOrNull() ?: 0

            val nuevoInmueble = Inmueble(
                precio = precio,
                direccion = direccion,
                antiguedad = antiguedad,
                fechaRegistro = LocalDate.now() // Asumiendo que quieres registrar la fecha actual como parte del inmueble
            )

            try {
                crudService = CRUDService(this)
                crudService.addInmueble(nuevoInmueble)
                Toast.makeText(this, "Inmueble creado con éxito", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Error al crear el inmueble", Toast.LENGTH_SHORT).show()
            }

        }
    }
}
