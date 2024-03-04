import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class CRUDService(private val context: Context) {
    private val dbHelper = DBHelper(context)

    // Métodos para Inmobiliaria
    fun addInmobiliaria(inmobiliaria: Inmobiliaria) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nombre", inmobiliaria.nombre)
            put("direccion", inmobiliaria.direccion)
        }
        db.insert("inmobiliarias", null, values)
        db.close()
    }

    fun leerInmobiliarias(): List<Inmobiliaria> {
        val inmobiliarias = mutableListOf<Inmobiliaria>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM inmobiliarias", null)
        if (cursor.moveToFirst()) {
            do {
                val inmobiliaria = Inmobiliaria(
                    nombre = cursor.getString(cursor.getColumnIndex("nombre")),
                    direccion = cursor.getString(cursor.getColumnIndex("direccion")),
                )
                inmobiliarias.add(inmobiliaria)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return inmobiliarias
    }

    fun actualizarInmobiliaria(nombre: String, nuevaInmobiliaria: Inmobiliaria) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("direccion", nuevaInmobiliaria.direccion)
        }
        db.update("inmobiliarias", values, "nombre = ?", arrayOf(nombre))
        db.close()
    }

    fun eliminarInmobiliaria(nombre: String) {
        val db = dbHelper.writableDatabase
        db.delete("inmobiliarias", "nombre = ?", arrayOf(nombre))
        db.close()
    }

    // Métodos para Inmueble
    fun addInmueble(inmueble: Inmueble) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("precio", inmueble.precio)
            put("direccion", inmueble.direccion)
            put("antiguedad", inmueble.antiguedad)
        }
        db.insert("inmuebles", null, values)
        db.close()
    }

    fun leerInmuebles(): List<Inmueble> {
        val inmuebles = mutableListOf<Inmueble>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM inmuebles", null)
        if (cursor.moveToFirst()) {
            do {
                val inmueble = Inmueble(
                    precio = cursor.getDouble(cursor.getColumnIndex("precio")),
                    direccion = cursor.getString(cursor.getColumnIndex("direccion")),
                    antiguedad = cursor.getInt(cursor.getColumnIndex("antiguedad"))
                )
                inmuebles.add(inmueble)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return inmuebles
    }

    fun actualizarInmueble(direccion: String, nuevoInmueble: Inmueble) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("precio", nuevoInmueble.precio)
            put("antiguedad", nuevoInmueble.antiguedad)
        }
        db.update("inmuebles", values, "direccion = ?", arrayOf(direccion))
        db.close()
    }

    fun eliminarInmueble(direccion: String) {
        val db = dbHelper.writableDatabase
        db.delete("inmuebles", "direccion = ?", arrayOf(direccion))
        db.close()
    }
}
