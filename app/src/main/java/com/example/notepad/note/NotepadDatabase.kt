package com.example.notepad.note


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import com.example.notepad.db.DbHelper

class NotepadDatabase(context: Context) {
    private val databaseHelper = DbHelper(context)

    fun insert(title: String, text: String) {
        val db = databaseHelper.writableDatabase
        val values = ContentValues().apply {
            put("notepad_title", title)
            put("notepad_text", text)
        }

        db.insert("notepad", null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun setSearchDB(searchText: String): ArrayList<Note> {
        val list = ArrayList<Note>()
        val db = databaseHelper.readableDatabase
        val selection = "notepad_title like ?"
        val cursor = db.query(
            "notepad",
            null,
            selection,
            arrayOf("%$searchText%"),
            null,
            null,
            null
        )

        var id: Int
        var title: String
        var text: String


        while (cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex("id"))
            title = cursor.getString(cursor.getColumnIndex("notepad_title"))
            text = cursor.getString(cursor.getColumnIndex("notepad_text"))
            val item = Note(
                id = id,
                title = title,
                text = text
            )
            list.add(item)
        }
        cursor.close()
        db.close()

        return list
    }

    fun update(id: Int, title: String, text: String) {
        val db = databaseHelper.writableDatabase

        val values = ContentValues().apply {
            put("notepad_title", title)
            put("notepad_text", text)
        }
        db.update("notepad", values, "id = ?", arrayOf(id.toString()))

        db.close()
    }

    fun delete(id: Int) {
        val db = databaseHelper.writableDatabase
        db.delete("notepad", "id = ?", arrayOf(id.toString()))
        db.close()
    }

    data class Note(val id: Int, val title: String, val text: String)

}