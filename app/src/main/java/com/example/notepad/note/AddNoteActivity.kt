package com.example.notepad.note

import android.content.Intent
import android.os.Bundle
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

class AddNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_note)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Добавление записи"

        val titleAddNote: EditText = findViewById(R.id.note_list_title)
        val textAddNote: EditText = findViewById(R.id.note_list_text)
        val buttonAddNote: Button = findViewById(R.id.note_list_add)

        buttonAddNote.setOnClickListener {
            val title = titleAddNote.text.toString().trim()
            val text = textAddNote.text.toString().trim()

            if (title == "")
                Toast.makeText(this, "Название не заполнено", Toast.LENGTH_LONG).show()
            else {
                val db = NotepadDatabase(this)
                db.insert(title, text)

                Toast.makeText(this, "Запись $title добавлена", Toast.LENGTH_LONG).show()

                titleAddNote.text.clear()
                textAddNote.text.clear()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()
        return true
    }

}