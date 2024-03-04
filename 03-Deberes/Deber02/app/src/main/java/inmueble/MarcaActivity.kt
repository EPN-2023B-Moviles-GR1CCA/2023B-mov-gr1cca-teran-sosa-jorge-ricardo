
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class InmuebleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inmueble_activity) // Asegúrate de que este layout existe

        val btnAddInmueble = findViewById<Button>(R.id.btnAddInmueble)
        val btnReadInmueble = findViewById<Button>(R.id.btnReadInmueble)
        val btnDeleteInmueble = findViewById<Button>(R.id.btnDeleteInmueble)
        val btnUpdateInmueble = findViewById<Button>(R.id.btnUpdateInmueble)

        btnAddInmueble.setOnClickListener {
            val intent = Intent(this, AddInmuebleActivity::class.java)
            startActivity(intent)
        }
        btnReadInmueble.setOnClickListener {
            val intent = Intent(this, ReadInmuebleActivity::class.java)
            startActivity(intent)
        }
        btnDeleteInmueble.setOnClickListener {
            val intent = Intent(this, DeleteInmuebleActivity::class.java)
            startActivity(intent)
        }
        btnUpdateInmueble.setOnClickListener {
            val intent = Intent(this, UpdateInmuebleActivity::class.java)
            startActivity(intent)
        }
    }
}
