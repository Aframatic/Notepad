package com.example.notepad.note

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notepad.MainActivity
import com.example.notepad.R
import com.example.notepad.category_note.CategoryNoteActivity

class EditNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_note)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Редактирование"

        val title: EditText = findViewById(R.id.edit_note_list_title)
        val desc: EditText = findViewById(R.id.edit_note_list_text)
        val editNoteButton: Button = findViewById(R.id.edit_note_list)
        val deleteNoteButton: Button = findViewById(R.id.delete_note_list)

        val oldTitle = intent.getStringExtra("notepad_title")
        val oldText = intent.getStringExtra("notepad_text")
        val noteId = intent.getIntExtra("notepad_id", 0)

        title.text = Editable.Factory.getInstance().newEditable(oldTitle)
        desc.text = Editable.Factory.getInstance().newEditable(oldText)

        editNoteButton.setOnClickListener {
            val newTitle = title.text.toString().trim()
            val newDesc = desc.text.toString().trim()

            if (newTitle == "")
                Toast.makeText(this, "Название не заполнено", Toast.LENGTH_LONG).show()
            else {
                val db = NotepadDatabase(this)
                db.update(noteId, newTitle, newDesc)

                Toast.makeText(this, "Запись $newTitle изменена", Toast.LENGTH_LONG).show()

                title.text.clear()
                desc.text.clear()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        deleteNoteButton.setOnClickListener {
            val db = NotepadDatabase(this)
            db.delete(noteId)
            val newTitle = title.text.toString().trim()
            Toast.makeText(this, "Запись $newTitle удалена", Toast.LENGTH_LONG).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val noteId = intent.getIntExtra("notepad_id", 0)

        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }

            R.id.add_category_note -> {
                val intent = Intent(this, CategoryNoteActivity::class.java)
                intent.putExtra("notepad_id", noteId)
                startActivity(intent)
            }
        }
        return true
    }
}