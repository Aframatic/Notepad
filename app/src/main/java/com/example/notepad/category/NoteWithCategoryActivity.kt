package com.example.notepad.category

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.R

class NoteWithCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_category_note)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val nameCategory = intent.getStringExtra("category_name")
        val idCategory = intent.getIntExtra("category_id", 0)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "$nameCategory"

        val recyclerView: RecyclerView = findViewById(R.id.notesListCategory)
        var noteCategoryAdapter = NoteWithCategoryAdapter(emptyList(), this)
        recyclerView.adapter = noteCategoryAdapter

        val database = CategoryDatabase(this)

        val categoryList = database.getNoteCategory(idCategory)
        noteCategoryAdapter = NoteWithCategoryAdapter(categoryList, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = noteCategoryAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.category_delete_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, CategoryActivity::class.java)
                startActivity(intent)
            }

            R.id.delete_category -> {
                val categoryId = intent.getIntExtra("category_id", 0)
                val db = CategoryDatabase(this)
                db.delete(categoryId)

                val intent = Intent(this, CategoryActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }
}