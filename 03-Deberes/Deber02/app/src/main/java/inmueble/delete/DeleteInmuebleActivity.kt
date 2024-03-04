import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DeleteInmuebleActivity : ComponentActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adaptador: InmueblesEliminarAdapter
    private lateinit var crudService: CRUDService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inmueble_delete_activity) // Asegúrate de tener este layout correspondiente

        recyclerView = findViewById(R.id.inmueblesEliminarView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        crudService = CRUDService(this)

        actualizarLista()
    }

    private fun eliminarInmueble(direccion: String) {
        crudService.eliminarInmueble(direccion)
        actualizarLista()
    }

    private fun actualizarLista() {
        val inmuebles = crudService.leerInmuebles()
        adaptador = InmueblesEliminarAdapter(inmuebles, this::eliminarInmueble)
        recyclerView.adapter = adaptador
    }
}
