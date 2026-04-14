package com.example.notepad.category_note

import android.content.ContentValues
import android.content.Context
import com.example.notepad.db.DbHelper

class CategoryNoteDatabase(context: Context) {
    private val databaseHelper = DbHelper(context)

    fun insert(note_id: Int, category_id: Int) {
        val db = databaseHelper.writableDatabase
        val values = ContentValues().apply {
            put("note_id", note_id)
            put("category_id", category_id)
        }

        db.insert("categoryNote", null, values)
        db.close()
    }

    fun checkFirstDB(noteId: Int, categoryId: Int): Boolean {
        val db = databaseHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM categoryNote WHERE note_id = '" + noteId + "' AND category_id = '" + categoryId + "'",
            null
        )
        var status = false
        if (cursor.moveToFirst()) {
            status = true
        }
        cursor.close()
        db.close()

        return status
    }

    fun getCategory(noteId: Int, categoryId: Int): Boolean {
        val db = databaseHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM categoryNote WHERE note_id = ? and category_id = ?",
            arrayOf(noteId.toString(), categoryId.toString())
        )
        var status = false
        if (cursor.moveToFirst()) {
            status = true
        }

        cursor.close()
        db.close()

        return status
    }

    fun delete(noteId: Int, categoryId: Int) {
        val db = databaseHelper.writableDatabase
        db.delete(
            "categoryNote",
            "note_id = ? and category_id = ?",
            arrayOf(noteId.toString(), categoryId.toString())
        )
        db.close()
    }

    data class CategoryNote(val note_id: Int, val category_id: Int)
}