package com.example.examenibricardoteran.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context?) : SQLiteOpenHelper(
    context,
    "DbExamenRT.db",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaInmobiliaria =
            "CREATE TABLE t_inmobiliaria(" +
                    "id_inmobiliaria INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre_inmobiliaria TEXT NOT NULL," +
                    "direccion_inmobiliaria TEXT NOT NULL);"

        val scriptSQLCrearTablaInmueble =
            "CREATE TABLE t_inmueble(" +
                    "id_inmueble INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "tipo_inmueble TEXT NOT NULL," +
                    "direccion_inmueble TEXT NOT NULL," +
                    "antiguedad_inmueble TEXT NOT NULL," +
                    "valor_inmueble TEXT NOT NULL, " +
                    "IdInmobiliaria INTEGER NOT NULL," +
                    "FOREIGN KEY(IdInmobiliaria) REFERENCES t_inmobiliaria(id_inmobiliaria));"

        db?.execSQL(scriptSQLCrearTablaInmobiliaria)
        db?.execSQL(scriptSQLCrearTablaInmueble)
    }

    // Se ejecuta cuando la version cambia
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS t_inmueble")
        db?.execSQL("DROP TABLE IF EXISTS t_inmobiliaria")
        onCreate(db)
    }
}