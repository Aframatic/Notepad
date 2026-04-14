package com.example.notepad

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.category.CategoryActivity
import com.example.notepad.note.AddNoteActivity
import com.example.notepad.note.NotepadDatabase
import com.example.notepad.note.NotesAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var noteRecyclerView: RecyclerView
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var database: NotepadDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.title = "Главная"

        noteRecyclerView = findViewById(R.id.notesList)
        notesAdapter = NotesAdapter(emptyList(), this)
        noteRecyclerView.adapter = notesAdapter

        database = NotepadDatabase(this)

        val notesList = database.setSearchDB("")
        notesAdapter = NotesAdapter(notesList, this)
        noteRecyclerView.layoutManager = LinearLayoutManager(this)
        noteRecyclerView.adapter = notesAdapter

        initSearchView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    fun initSearchView() {
        val searchView: SearchView = findViewById(R.id.searchView)
        val thisIs = this

        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                val notesList = database.setSearchDB(p0!!)
                notesAdapter = NotesAdapter(notesList, thisIs)
                noteRecyclerView.adapter = notesAdapter
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
                Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show()
            }

            R.id.category -> {
                val intent = Intent(this, CategoryActivity::class.java)
                startActivity(intent)
            }

            R.id.add_note -> {
                val intent = Intent(this, AddNoteActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }

}
