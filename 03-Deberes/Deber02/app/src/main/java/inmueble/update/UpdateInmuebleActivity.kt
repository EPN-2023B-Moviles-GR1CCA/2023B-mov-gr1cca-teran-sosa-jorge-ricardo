
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.ComponentActivity


class UpdateInmuebleActivity : ComponentActivity() {

    private lateinit var listView: ListView
    private val crudService = CRUDService(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inmueble_update_activity) // Asegúrate de que este layout existe

        listView = findViewById(R.id.listViewInmuebles)
        mostrarInmuebles()
    }

    private fun mostrarInmuebles() {
        val inmuebles = crudService.leerInmuebles()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, inmuebles.map { "${it.direccion} - ${it.precio}" })
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, UpdateSingleInmuebleActivity::class.java)
            // Suponiendo que la 'direccion' es la clave única para cada inmueble
            intent.putExtra("direccionInmueble", inmuebles[position].direccion)
            startActivity(intent)
        }
    }
}
