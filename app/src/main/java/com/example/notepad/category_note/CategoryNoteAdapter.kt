package com.example.notepad.category_note

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.R
import com.example.notepad.category.CategoryDatabase

class CategoryNoteAdapter(
    private val categoryNoteList: List<CategoryDatabase.Category>,
    var context: Context,
    val noteId: Int
) : RecyclerView.Adapter<CategoryNoteAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CategoryViewHolder {
        val view =
            LayoutInflater.from(p0.context).inflate(R.layout.activity_category_note_list, p0, false)

        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(p0: CategoryViewHolder, p1: Int) {
        fun checkedCategory(): Boolean {
            val database = CategoryNoteDatabase(context)
            val status = database.getCategory(noteId, categoryNoteList[p1].id)

            return status
        }

        val categoryNote = categoryNoteList[p1]
        p0.bind(categoryNote, checkedCategory())

        p0.btn.setOnClickListener {
            val database = CategoryNoteDatabase(context)
            if (!database.checkFirstDB(noteId, categoryNoteList[p1].id)) {
                database.insert(noteId, categoryNoteList[p1].id)
            } else {
                database.delete(noteId, categoryNoteList[p1].id)
            }
        }
    }

    override fun getItemCount(): Int = categoryNoteList.size

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val btn: CheckBox = itemView.findViewById(R.id.category_note_list_checkBox)
        fun bind(categoryNote: CategoryDatabase.Category, status: Boolean) {
            itemView.findViewById<TextView>(R.id.category_note_list_name).text = categoryNote.name
            itemView.findViewById<CheckBox>(R.id.category_note_list_checkBox).isChecked = status
        }
    }
}

