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

class CategoryAdapter(
    private val categoryList: List<CategoryDatabase.Category>, var context: Context) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CategoryViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.activity_category_list, p0, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(p0: CategoryViewHolder, p1: Int) {
        val category = categoryList[p1]
        p0.bind(category)

        p0.btn.setOnClickListener {
            val intent = Intent(context, NoteWithCategoryActivity::class.java)
            intent.putExtra("category_name", categoryList[p1].name)
            intent.putExtra("category_id", categoryList[p1].id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = categoryList.size

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btn: LinearLayout = itemView.findViewById(R.id.button_category)
        fun bind(category: CategoryDatabase.Category) {
            itemView.findViewById<TextView>(R.id.category_list_name).text = category.name
        }
    }

}

