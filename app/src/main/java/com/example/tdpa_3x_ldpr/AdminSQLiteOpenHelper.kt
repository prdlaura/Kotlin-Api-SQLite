package com.example.tdpa_3x_ldpr

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AdminSQLiteOpenHelper (
    context: Context,
    name: String,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
):  SQLiteOpenHelper(context,name,factory,version)
{
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE estudiantes(id integer primary key autoincrement, nombre text, nombreMateria text, primerCal double, segundaCal double)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

}