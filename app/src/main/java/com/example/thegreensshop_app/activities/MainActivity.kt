package com.example.thegreensshop_app.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thegreensshop_app.adapters.CategoryAdapter
import com.example.thegreensshop_app.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        // Initialize RecyclerView and set adapter
        val recyclerView = findViewById<RecyclerView>(R.id.category_recyclerview)

        val categories = getCategories()
        val categoryAdapter = CategoryAdapter(this, categories)

        recyclerView.adapter = categoryAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    // Return a list of Category objects
    private fun getCategories(): List<CategoryAdapter.Category> {
        val categories = mutableListOf<CategoryAdapter.Category>()
        categories.add(CategoryAdapter.Category("All", R.drawable.potted_plant_50))
        categories.add(CategoryAdapter.Category("House Plants", R.drawable.potted_plant_50))
        categories.add(CategoryAdapter.Category("Pots", R.drawable.potted_plant_50))
        categories.add(CategoryAdapter.Category("Care", R.drawable.potted_plant_50))
        categories.add(CategoryAdapter.Category("Accessories", R.drawable.potted_plant_50))
        return categories
    }
}
