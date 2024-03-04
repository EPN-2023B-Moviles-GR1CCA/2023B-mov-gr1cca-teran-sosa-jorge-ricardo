import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "InmueblesDB"

        private const val TABLE_INMUEBLES = "inmuebles"
        private const val COLUMN_INMUEBLE_PRECIO = "precio"
        private const val COLUMN_INMUEBLE_DIRECCION = "direccion"
        private const val COLUMN_INMUEBLE_ANTIGUEDAD = "antiguedad"

        private const val TABLE_INMOBILIARIAS = "inmobiliarias"
        private const val COLUMN_INMOBILIARIA_NOMBRE = "nombre"
        private const val COLUMN_INMOBILIARIA_DIRECCION = "direccion"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createInmueblesTable = ("CREATE TABLE $TABLE_INMUEBLES (" +
                "$COLUMN_INMUEBLE_DIRECCION TEXT PRIMARY KEY," +
                "$COLUMN_INMUEBLE_PRECIO REAL," +
                "$COLUMN_INMUEBLE_ANTIGUEDAD INTEGER" +
                ")")
        db.execSQL(createInmueblesTable)

        val createInmobiliariasTable = ("CREATE TABLE $TABLE_INMOBILIARIAS (" +
                "$COLUMN_INMOBILIARIA_NOMBRE TEXT PRIMARY KEY," +
                "$COLUMN_INMOBILIARIA_DIRECCION TEXT" +
                ")")
        db.execSQL(createInmobiliariasTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_INMUEBLES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_INMOBILIARIAS")
        onCreate(db)
    }
}
