import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class CRUDService {
    private val inmobiliariasFileName = "inmobiliarias.json"
    private val inmueblesFileName = "inmuebles.json"
    private val gson = Gson()

    fun addInmobiliaria(inmobiliaria: Inmobiliaria) {
        val inmobiliarias = leerInmobiliarias().toMutableList()
        inmobiliarias.add(inmobiliaria)
        guardarInmobiliarias(inmobiliarias)
    }

    fun leerInmobiliarias(): List<Inmobiliaria> {
        val typeToken = object : TypeToken<List<Inmobiliaria>>() {}
        return leerArchivo(inmobiliariasFileName, typeToken) ?: emptyList()
    }

    fun actualizarInmobiliaria(nombre: String, nuevaInmobiliaria: Inmobiliaria) {
        val inmobiliarias = leerInmobiliarias().toMutableList()
        val index = inmobiliarias.indexOfFirst { it.nombre == nombre }
        if (index != -1) {
            inmobiliarias[index] = nuevaInmobiliaria
            guardarInmobiliarias(inmobiliarias)
        }
    }

    fun eliminarInmobiliaria(nombre: String) {
        val inmobiliarias = leerInmobiliarias().filter { it.nombre != nombre }
        guardarInmobiliarias(inmobiliarias)
    }

    private fun guardarInmobiliarias(inmobiliarias: List<Inmobiliaria>) {
        guardarArchivo(inmobiliariasFileName, inmobiliarias)
    }

    fun addInmueble(inmueble: Inmueble) {
        val inmuebles = leerInmuebles().toMutableList()
        inmuebles.add(inmueble)
        guardarInmuebles(inmuebles)
    }

    fun leerInmuebles(): List<Inmueble> {
        val typeToken = object : TypeToken<List<Inmueble>>() {}
        return leerArchivo(inmueblesFileName, typeToken) ?: emptyList()
    }

    fun actualizarInmueble(tipo: String, nuevoInmueble: Inmueble) {
        val inmuebles = leerInmuebles().toMutableList()
        val index = inmuebles.indexOfFirst { it.tipo == tipo }
        if (index != -1) {
            inmuebles[index] = nuevoInmueble
            guardarInmuebles(inmuebles)
        }
    }

    fun eliminarInmueble(tipo: String) {
        val inmuebles = leerInmuebles().filter { it.tipo != tipo }
        guardarInmuebles(inmuebles)
    }

    fun guardarInmuebles(inmuebles: List<Inmueble>) {
        guardarArchivo(inmueblesFileName, inmuebles)
    }

    fun <T> leerArchivo(fileName: String, typeToken: TypeToken<List<T>>): List<T> {
        val file = File(fileName)

        if (!file.exists() || file.readText().isEmpty()) return emptyList()

        val json = file.readText()
        return try {
            Gson().fromJson(json, typeToken.type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun <T> guardarArchivo(fileName: String, data: List<T>) {
        val json = gson.toJson(data)
        File(fileName).writeText(json)
    }
}
