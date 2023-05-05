package com.example.thegreensshop_app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.thegreensshop_app.R
import com.example.thegreensshop_app.categories.*

class CategoryAdapter(
    private val categories: List<Category>,
    private val categoryClickListener: CategoryClickListener
) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    interface CategoryClickListener {
        fun onCategoryClicked(category: Category)
    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoryNameTextView: TextView =
            itemView.findViewById(R.id.category_name_textview)
        private val categoryImageView: ImageView =
            itemView.findViewById(R.id.category_imageview)
        private val context: Context = itemView.context

        fun bind(category: Category, categoryClickListener: CategoryClickListener) {
            categoryNameTextView.text = category.name
            categoryImageView.setImageResource(category.imageResource)

            itemView.setOnClickListener {
                categoryClickListener.onCategoryClicked(category)
            }
        }

        private fun getCategoryActivity(categoryName: String): Class<out AppCompatActivity> {
            return when (categoryName) {
                "Houseplants" -> HouseplantsActivity::class.java
                "Pots" -> PotsActivity::class.java
                "Care" -> CareActivity::class.java
                "Accessories" -> AccessoriesActivity::class.java
                else -> AllCategoriesActivity::class.java
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_category_adapter, parent, false)
        return CategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position], categoryClickListener)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    data class Category(val name: String, val imageResource: Int)
}
