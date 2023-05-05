package com.example.thegreensshop_app.categories

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thegreensshop_app.R
import com.example.thegreensshop_app.adapters.ProductAdapter
import com.example.thegreensshop_app.models.Product

class CareActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_care)

        val productList = listOf(
            Product(
                R.drawable.product_care_1,
                "Olly Terrarium Medium Starter Kit",
                "1 Olly Terrarium Medium",
                59.99),
            Product(
                R.drawable.product_care_2,
                "Potting Soil",
                "Give your plants a boost with our potting soil! This universal soil mixture is the ideal breeding ground that helps your BabyPLNTS grow",
                29.99),
            Product(
                R.drawable.product_care_3,
                "Macrame",
                "The coloured cord is a triple twisted cord made completely out of 100% recycled cotton. The cord is twisted with three wires to form one thread with a thickness of 5mm. ",
                49.99),
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ProductAdapter(productList)
        recyclerView.adapter = adapter

    }
}