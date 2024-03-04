import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ReadInmuebleActivity : ComponentActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var inmueblesReadAdapter: InmueblesReadAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inmueble_read_activity) // Asegúrate de tener este layout

        recyclerView = findViewById(R.id.inmueblesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val crudService = CRUDService(this)
        val inmuebles = crudService.leerInmuebles()

        inmueblesReadAdapter = InmueblesReadAdapter(inmuebles)
        recyclerView.adapter = inmueblesReadAdapter
    }
}
