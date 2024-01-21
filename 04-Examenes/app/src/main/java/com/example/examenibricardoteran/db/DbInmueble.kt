package com.example.examenibricardoteran.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class DbInmueble(
    private var idInmueble: Int?,
    private var tipoInmueble: String,
    private var direccionInmueble: String,
    private var antiguedadInmueble: String,
    private var valorInmueble: String,
    private var idInmobiliaria: Int?,
    private val context: Context?
) {
    init {
        idInmueble
        tipoInmueble
        direccionInmueble
        antiguedadInmueble
        valorInmueble
        idInmobiliaria
        context
    }

    fun setidInmueble(idInmueble: Int) {
        this.idInmueble = idInmueble
    }
    fun settipoInmueble(tipoInmueble: String) {
        this.tipoInmueble = tipoInmueble
    }
    fun setdireccionInmueble(direccionInmueble: String) {
        this.direccionInmueble = direccionInmueble
    }
    fun setantiguedadInmueble(antiguedadInmueble: String) {
        this.antiguedadInmueble = antiguedadInmueble
    }
    fun setvalorInmueble(valorInmueble: String) {
        this.valorInmueble = valorInmueble
    }
    fun setidInmobiliaria(idInmobiliaria: Int) {
        this.idInmobiliaria = idInmobiliaria
    }
    fun getidInmueble(): Int? {
        return idInmueble
    }
    fun gettipoInmueble(): String {
        return tipoInmueble
    }
    fun getdireccionInmueble(): String {
        return direccionInmueble
    }
    fun getantiguedadInmueble(): String {
        return antiguedadInmueble
    }
    fun getvalorInmueble(): String {
        return valorInmueble
    }
    fun getidInmobiliaria(): Int? {
        return idInmobiliaria
    }

    fun insertInmueble(): Long {
        val dbHelper: DbHelper = DbHelper(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        val values: ContentValues = ContentValues()
        values.put("tipo_inmueble", this.tipoInmueble)
        values.put("direccion_inmueble", this.direccionInmueble)
        values.put("antiguedad_inmueble", this.antiguedadInmueble)
        values.put("valor_inmueble", this.valorInmueble)
        values.put("IdInmobiliaria", this.idInmobiliaria)

        return db.insert("t_inmueble", null, values)
    }

    fun showInmuebleBy(id: Int): ArrayList<DbInmueble> {
        val dbHelper: DbHelper = DbHelper(this.context)
        val db: SQLiteDatabase = dbHelper.readableDatabase

        var lista = ArrayList<DbInmueble>()
        var inmueble: DbInmueble? = null

        var cursor: Cursor? = null

        cursor = db.rawQuery("SELECT * FROM t_inmueble WHERE id_inmueble = ${id+1}", null)

        if (cursor.moveToFirst()) {
            do {
                inmueble = DbInmueble(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5),
                    this.context
                )
                lista.add(inmueble)
            } while (cursor.moveToNext())
        }

        return lista
    }

    fun showInmueble(): ArrayList<DbInmueble> {
        val dbHelper: DbHelper = DbHelper(this.context)
        val db: SQLiteDatabase = dbHelper.readableDatabase

        var lista = ArrayList<DbInmueble>()
        var inmueble: DbInmueble? = null

        var cursor: Cursor? = null

        cursor = db.rawQuery("SELECT * FROM t_inmueble", null)

        if (cursor.moveToFirst()) {
            do {
                inmueble = DbInmueble(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5),
                    this.context
                )
                lista.add(inmueble)
            } while (cursor.moveToNext())
        }

        return lista
    }

    fun getInmuebleById(id: Int): DbInmueble {
        val dbHelper = DbHelper(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        var inmueble = DbInmueble(null, "", "", "", "", null, this.context)
        var cursor: Cursor? = null

        cursor = db.rawQuery("SELECT * FROM t_inmueble WHERE id_inmueble = ${id+1}", null)

        if (cursor.moveToFirst()) {
            do {
                inmueble.setidInmueble(cursor.getInt(0))
                inmueble.settipoInmueble(cursor.getString(1))
                inmueble.setdireccionInmueble(cursor.getString(2))
                inmueble.setantiguedadInmueble(cursor.getString(3))
                inmueble.setvalorInmueble(cursor.getString(4))
                inmueble.setidInmobiliaria(cursor.getInt(5))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return inmueble
    }

    fun updateInmueble(): Int {
        val dbHelper = DbHelper(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        val values: ContentValues = ContentValues()
        values.put("tipo_inmueble", this.tipoInmueble)
        values.put("direccion_inmueble", this.direccionInmueble)
        values.put("antiguedad_inmueble", this.antiguedadInmueble)
        values.put("valor_inmueble", this.valorInmueble)
        values.put("IdInmobiliaria", this.idInmobiliaria)

        return db.update(
            "t_inmueble",
            values,
            "id_inmueble = ?",
            arrayOf(this.idInmueble.toString())
        )
    }

    fun deleteInmueble(): Int {
        val dbHelper = DbHelper(this.context)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        return db.delete(
            "t_inmueble",
            "id_inmueble = ?",
            arrayOf(this.idInmueble.toString())
        )
    }

    override fun toString(): String {
        val out =
            "idInmueble: " + idInmueble +
            "tipoInmueble: " + tipoInmueble +
            "direccionInmueble: " + direccionInmueble +
            "antiguedadInmueble: " + antiguedadInmueble +
            "valorInmueble: " + valorInmueble +
            "idInmobiliaria: " + idInmobiliaria
        return out
    }
}