package com.example.notepad.category

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import com.example.notepad.db.DbHelper
import com.example.notepad.note.Note

class CategoryDatabase(context: Context) {
    private val databaseHelper = DbHelper(context)

    fun insert(name: String) {
        val db = databaseHelper.writableDatabase
        val values = ContentValues().apply {
            put("category_name", name)
        }

        db.insert("category", null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getNoteCategory(categoryId: Int): List<Note> {
        val list = mutableListOf<Note>()
        val db = databaseHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM notepad as N left join categoryNote as CN on N.id = CN.note_id WHERE CN.category_id = ?",
            arrayOf(categoryId.toString())
        )

        var id: Int
        var title: String
        var text: String

        while (cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex("id"))
            title = cursor.getString(cursor.getColumnIndex("notepad_title"))
            text = cursor.getString(cursor.getColumnIndex("notepad_text"))
            list.add(Note(id, title, text))
        }

        cursor.close()
        db.close()

        return list
    }

    @SuppressLint("Range")
    fun getCategory(): List<Category> {

        val list = mutableListOf<Category>()
        val db = databaseHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM category", null)

        var id: Int
        var name: String

        while (cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex("id"))
            name = cursor.getString(cursor.getColumnIndex("category_name"))
            list.add(Category(id, name))
        }

        cursor.close()
        db.close()

        return list
    }

    fun delete(id: Int) {
        val db = databaseHelper.writableDatabase
        db.delete("category", "id = ?", arrayOf(id.toString()))
        db.close()
    }

    data class Category(val id: Int, val name: String)
}