package com.example.notepad.category_note

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.R
import com.example.notepad.category.CategoryDatabase

class CategoryNoteActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_category_note)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Категории"

        val noteId = intent.getIntExtra("notepad_id", 0)
        val recyclerView: RecyclerView = findViewById(R.id.notesListCategory)
        var categoryNoteAdapter = CategoryNoteAdapter(emptyList(), this, noteId)
        recyclerView.adapter = categoryNoteAdapter

        val database = CategoryDatabase(this)

        val categoryList = database.getCategory()
        categoryNoteAdapter = CategoryNoteAdapter(categoryList, this, noteId)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = categoryNoteAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }

}