package com.example.thegreensshop_app.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.thegreensshop_app.adapters.CategoryAdapter
import com.example.thegreensshop_app.adapters.MainPageAdapter
import com.example.thegreensshop_app.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

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

        // Initialize ViewPager2 and TabLayout
        viewPager = findViewById(R.id.viewPager2)
        tabLayout = findViewById(R.id.tab_layout)

        val pagerAdapter = MainPageAdapter(this)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.shop)
                1 -> tab.text = getString(R.string.cart)
                2 -> tab.text = getString(R.string.orders)
                3 -> tab.text = getString(R.string.account)
            }
        }.attach()
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
