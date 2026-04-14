package com.example.notepad.note

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.R

class NotesAdapter(private val notesList: List<NotepadDatabase.Note>, var context: Context) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NoteViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.activity_notes, p0, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(p0: NoteViewHolder, p1: Int) {
        val note = notesList[p1]
        p0.bind(note)

        p0.btn.setOnClickListener {
            val intent = Intent(context, EditNoteActivity::class.java)
            intent.putExtra("notepad_title", notesList[p1].title)
            intent.putExtra("notepad_text", notesList[p1].text)
            intent.putExtra("notepad_id", notesList[p1].id)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = notesList.size

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btn: LinearLayout = itemView.findViewById(R.id.button_note)

        fun bind(note: NotepadDatabase.Note) {
            itemView.findViewById<TextView>(R.id.note_list_title).text = note.title
            itemView.findViewById<TextView>(R.id.note_list_desc).text = note.text
        }
    }

}