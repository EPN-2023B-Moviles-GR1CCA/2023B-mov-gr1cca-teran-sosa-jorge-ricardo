

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.ComponentActivity

class ReadInmueblesActivity : ComponentActivity() {
    private lateinit var listViewInmuebles: ListView
    private lateinit var crudService: CRUDService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inmuebles_read_activity) // Asumiendo que hay un archivo de layout correspondiente

        listViewInmuebles = findViewById(R.id.listViewInmuebles)
        crudService = CRUDService(this)

        cargarInmuebles()
    }

    private fun cargarInmuebles() {
        val inmuebles = crudService.leerInmuebles()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, inmuebles.map { "${it.direccion} - Precio: ${it.precio} - Antigüedad: ${it.antiguedad} años" })
        listViewInmuebles.adapter = adapter
    }
}
