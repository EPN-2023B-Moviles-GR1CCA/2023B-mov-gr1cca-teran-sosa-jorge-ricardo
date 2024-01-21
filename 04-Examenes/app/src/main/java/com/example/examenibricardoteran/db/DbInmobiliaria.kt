package com.example.examenibricardoteran.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class DbInmobiliaria(
    private var idInmobiliaria: Int?,
    private var nombreInmobiliaria: String,
    private var direccionInmobiliaria: String,
    private val context: Context?
) {
    init {
        idInmobiliaria
        nombreInmobiliaria
        direccionInmobiliaria
        context
    }

    fun setidInmobiliaria(idInmobiliaria: Int) {
        this.idInmobiliaria = idInmobiliaria
    }

    fun setnombreInmobiliaria(nombreInmobiliaria: String) {
        this.nombreInmobiliaria = nombreInmobiliaria
    }

    fun setdireccionInmobiliaria(direccionInmobiliaria: String) {
        this.direccionInmobiliaria = direccionInmobiliaria
    }

    fun getidInmobiliaria(): Int? {
        return idInmobiliaria
    }

    fun getnombreInmobiliaria(): String {
        return nombreInmobiliaria
    }

    fun getdireccionInmobiliaria(): String {
        return direccionInmobiliaria
    }

    fun insertInmobiliaria(): Long {
        val dbHelper: DbHelper = DbHelper(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        val values: ContentValues = ContentValues()
        values.put("nombre_inmobiliaria", this.nombreInmobiliaria)
        values.put("direccion_inmobiliaria", this.direccionInmobiliaria)

        return db.insert("t_inmobiliaria", null, values)
    }

    fun showInmobiliaria(): ArrayList<DbInmobiliaria> {
        val dbHelper: DbHelper = DbHelper(this.context)
        val db: SQLiteDatabase = dbHelper.readableDatabase

        var lista = ArrayList<DbInmobiliaria>()
        var inmobiliaria: DbInmobiliaria? = null
        var cursor: Cursor? = null

        cursor = db.rawQuery("SELECT * FROM t_inmobiliaria", null)

        if (cursor.moveToFirst()){
            do {
                inmobiliaria = DbInmobiliaria(
                    null, "", "", null
                )
                inmobiliaria.setidInmobiliaria(cursor.getInt(0))
                inmobiliaria.setnombreInmobiliaria(cursor.getString(1))
                inmobiliaria.setdireccionInmobiliaria(cursor.getString(2))
                lista.add(inmobiliaria)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return lista
    }

    fun getInmobiliariaById(id: Int): DbInmobiliaria {
        val dbHelper = DbHelper(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        var inmobiliaria = DbInmobiliaria(null, "", "", this.context)
        var cursor: Cursor? = null

        cursor = db.rawQuery("SELECT * FROM t_inmobiliaria WHERE id_inmobiliaria = ${id+1}", null)

        if (cursor.moveToFirst()) {
            do {
                inmobiliaria.setidInmobiliaria(cursor.getInt(0))
                inmobiliaria.setnombreInmobiliaria(cursor.getString(1))
                inmobiliaria.setdireccionInmobiliaria(cursor.getString(2))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return inmobiliaria
    }

    fun updateInmobiliaria(): Int {
        val dbHelper = DbHelper(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        val values: ContentValues = ContentValues()
        values.put("nombre_inmobiliaria", this.nombreInmobiliaria)
        values.put("direccion_inmobiliaria", this.direccionInmobiliaria)

        return db.update(
            "t_inmobiliaria",
            values,
            "id_inmobiliaria = ?",
            arrayOf(this.idInmobiliaria.toString())
        )
    }

    fun deleteInmobiliaria(id: Int): Int {
        val dbHelper = DbHelper(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        return db.delete(
            "t_inmobiliaria",
            "id_inmobiliaria = ?",
            arrayOf(this.idInmobiliaria.toString())
        )
    }

    override fun toString(): String {
        val out =
            "ID: ${this.idInmobiliaria}\n" +
            "Nombre: ${this.nombreInmobiliaria}\n" +
            "Direccion: ${this.direccionInmobiliaria}\n"
        return out
    }

}