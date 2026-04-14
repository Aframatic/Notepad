package com.example.notepad.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "notepad.db"
        private const val DATABASE_VERSION = 10

        private const val CREATE_TABLE_NOTE = """
            CREATE TABLE notepad (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                notepad_title TEXT,
                notepad_text TEXT
            )
        """

        private const val CREATE_TABLE_CATEGORY = """
            CREATE TABLE category (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                category_name TEXT
            )
        """

        private const val CREATE_TABLE_CATEGORY_NOTE = """
            CREATE TABLE categoryNote (
                note_id INTEGER REFERENCES notepad (id),
                category_id INTEGER REFERENCES category (id)
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_NOTE)
        db.execSQL(CREATE_TABLE_CATEGORY)
        db.execSQL(CREATE_TABLE_CATEGORY_NOTE)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS notepad")
        db.execSQL("DROP TABLE IF EXISTS category")
        db.execSQL("DROP TABLE IF EXISTS categoryNote")
        onCreate(db)
    }
}