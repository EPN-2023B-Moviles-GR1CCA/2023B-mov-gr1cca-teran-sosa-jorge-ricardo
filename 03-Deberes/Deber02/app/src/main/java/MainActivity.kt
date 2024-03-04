
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.example.joseguzmanochoaexamenb1.inmueble.InmuebleActivity
import com.example.joseguzmanochoaexamenb1.inmobiliaria.InmobiliariaActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Asegúrate de que este layout exista y tenga los botones correctos

        val btnInmuebles = findViewById<Button>(R.id.btnInmuebles) // Asegúrate de que este botón exista en tu layout
        val btnInmobiliarias = findViewById<Button>(R.id.btnInmobiliarias) // Asegúrate de que este botón exista en tu layout

        btnInmuebles.setOnClickListener {
            val intent = Intent(this, InmuebleActivity::class.java)
            startActivity(intent)
        }
        btnInmobiliarias.setOnClickListener {
            val intent = Intent(this, InmobiliariaActivity::class.java)
            startActivity(intent)
        }

    }
}
