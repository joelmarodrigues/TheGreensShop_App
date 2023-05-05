package com.example.thegreensshop_app.activities

import MainPageAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.thegreensshop_app.adapters.CategoryAdapter
import com.example.thegreensshop_app.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        // Initialize Firebase Authentication instance
        auth = FirebaseAuth.getInstance()

        // Check if the user is already authenticated
        if (auth.currentUser == null) {
            // Redirect to the login activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Initialize RecyclerView and set adapter
        val recyclerView = findViewById<RecyclerView>(R.id.category_recyclerview)

        val categories = getCategories()
        val categoryAdapter = CategoryAdapter(this, categories)

        recyclerView.adapter = categoryAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize ViewPager2
        viewPager = findViewById(R.id.viewPager2)

        val pagerAdapter = MainPageAdapter(this)
        viewPager.adapter = pagerAdapter

        // Initialize BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.shopFragment -> startActivity(Intent(this, MainActivity::class.java))
                R.id.cartFragment -> startActivity(Intent(this, CartActivity::class.java))
                R.id.ordersFragment -> startActivity(Intent(this, OrdersActivity::class.java))
                R.id.myAccountFragment -> startActivity(Intent(this, UserDetailsActivity::class.java))
            }
            true
        }
    }

    // Return a list of Category objects
    private fun getCategories(): List<CategoryAdapter.Category> {
        val categories = mutableListOf<CategoryAdapter.Category>()
        categories.add(CategoryAdapter.Category("All", R.drawable.all_plants))
        categories.add(CategoryAdapter.Category("Houseplants", R.drawable.houseplants))
        categories.add(CategoryAdapter.Category("Pots", R.drawable.pot))
        categories.add(CategoryAdapter.Category("Care", R.drawable.care))
        categories.add(CategoryAdapter.Category("Accessories", R.drawable.accessories))
        return categories
    }

    }

