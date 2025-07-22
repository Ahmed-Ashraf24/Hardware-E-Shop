package com.example.e_commerce.Utilities.UiAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commerce.Domain.Entity.Product
import com.example.e_commerce.R

class OrderAdapter(private val orderedList: List<Product>) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productPrice: TextView = itemView.findViewById(R.id.unit_price)
        val productCategory: TextView = itemView.findViewById(R.id.product_category)

        fun setImage(product: Product) {
            Glide.with(itemView.context).load(product.imageURL).into(productImage)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_ordered_item, parent, false)
        return OrderViewHolder(view)
    }

    override fun getItemCount(): Int = orderedList.size

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val product = orderedList[position]
        holder.setImage(product)
        holder.productName.text = product.name
        holder.productPrice.text = "${product.price} $"
        holder.productCategory.text = product.category
    }
}