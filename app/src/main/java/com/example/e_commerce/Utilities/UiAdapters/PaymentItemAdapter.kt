package com.example.e_commerce.Utilities.UiAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commerce.R
import com.example.e_commerce.Utilities.UIModule.CartItem

class PaymentItemAdapter (
    private val context: Context,
    private val productList: MutableList<CartItem>
) : RecyclerView.Adapter<PaymentItemAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productDetails: TextView = itemView.findViewById(R.id.product_details)
        val productQuantity: TextView = itemView.findViewById(R.id.product_quantity)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_payment_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val cartItem = productList[position]
        val product = cartItem.product

        holder.productName.text = product.name
        holder.productDetails.text = "${product.brand} • ${product.category}"
        holder.productQuantity.text = "Qty: ${cartItem.quantity}"
        holder.productPrice.text = "${String.format("%.2f", product.price * cartItem.quantity)}"

        Glide.with(context)
            .load(product.imageURL)
            .into(holder.productImage)    }





    override fun getItemCount(): Int = productList.size

    fun updateProducts(newProductList: List<CartItem>) {
        productList.clear()
        productList.addAll(newProductList)
        notifyDataSetChanged()
    }


}

