package com.example.notepad.category

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.R
import com.example.notepad.note.EditNoteActivity
import com.example.notepad.note.Note

class NoteWithCategoryAdapter(
    private val noteCategoryList: List<Note>, var context: Context
) : RecyclerView.Adapter<NoteWithCategoryAdapter.NoteCategoryViewHolder>() {

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NoteCategoryViewHolder {
            val view = LayoutInflater.from(p0.context).inflate(R.layout.activity_category_list, p0, false)
            return NoteCategoryViewHolder(view)
        }

        override fun onBindViewHolder(p0: NoteCategoryViewHolder, p1: Int) {
            val note = noteCategoryList[p1]
            p0.bind(note)

            p0.btn.setOnClickListener {
                val intent = Intent(context, EditNoteActivity::class.java)
                intent.putExtra("notepad_title", noteCategoryList[p1].title)
                intent.putExtra("notepad_text", noteCategoryList[p1].text)
                intent.putExtra("notepad_id", noteCategoryList[p1].id)
                context.startActivity(intent)
            }
        }

        override fun getItemCount(): Int = noteCategoryList.size

        class NoteCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val btn: LinearLayout = itemView.findViewById(R.id.button_category)
            fun bind(note: Note) {
                itemView.findViewById<TextView>(R.id.category_list_name).text = note.title
            }
        }
}