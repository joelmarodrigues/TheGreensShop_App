package com.example.thegreensshop_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.thegreensshop_app.R
import com.example.thegreensshop_app.models.Product

class ProductAdapter(private val productList: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    //Create a new ViewHolder, which defines the view that will be used for each item in the RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_product,
            parent,
            false
        )
        return ProductViewHolder(view)
    }

    //Return the number of items in the product list
    override fun getItemCount(): Int {
        return productList.size
    }

    //Fill the view with the data of the product in the specified position
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.productImage.setImageResource(product.image)
        holder.productName.text = product.name
        holder.productDescription.text = product.description
        holder.productPrice.text = product.price.toString()
    }

    //Class ViewHolder, which represents the view of an item in the product list
    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productDescription: TextView = itemView.findViewById(R.id.product_description)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
    }
}

