package com.example.notepad.category

import android.annotation.SuppressLint
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
import com.example.notepad.R

class AddCategoryActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_category)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Добавление категории"

        val nameAddCategory: EditText = findViewById(R.id.category_list_name)
        val buttonAddCategory: Button = findViewById(R.id.category_list_add)

        buttonAddCategory.setOnClickListener {
            val name = nameAddCategory.text.toString().trim()

            if (name == "")
                Toast.makeText(this, "Название не заполнено", Toast.LENGTH_LONG).show()
            else {
                val db = CategoryDatabase(this)
                db.insert(name)


                Toast.makeText(this, "Запись $title добавлена", Toast.LENGTH_LONG).show()

                nameAddCategory.text.clear()

                val intent = Intent(this, CategoryActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
        }
        return true
    }
}