package com.example.e_commerce.Utilities.UiAdapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commerce.Domain.Entity.Product
import com.example.e_commerce.R

class CartAdapter(private val cartList: List<Product>,val onQuantityChanged:(Float)->(Unit)) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
        var cartItemTotalPrice=0f
    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
        val productCategory: TextView = itemView.findViewById(R.id.product_category)
        val productQuantity: TextView = itemView.findViewById(R.id.quantity_text)
        val totalPriceTextView: TextView = itemView.findViewById(R.id.subtotal_amount)
        var totalPriceValue: Float = 0f
        var quantity=0


        fun setImage(product: Product) {
            Glide.with(itemView.context)
                .load(product.imageURL)
                .into(productImage)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart_product, parent, false)
        return CartViewHolder(view)
    }

    override fun getItemCount(): Int = cartList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = cartList[position]
        holder.setImage(product)
        holder.productName.text = product.name
        holder.productPrice.text = "${product.price} $"
        holder.productCategory.text = product.category
        holder.totalPriceValue = product.price
        holder.totalPriceTextView.text = "${holder.totalPriceValue} $"
        holder.quantity=holder.productQuantity.text.toString().toInt()
        holder.itemView.findViewById<CardView>(R.id.increase_button).setOnClickListener {
            holder.apply {
                this.productQuantity.text = this.productQuantity.text.toString()
                    .toInt()
                    .inc()
                    .toString()
                this.quantity++
                this.totalPriceTextView.text = product.price.times(quantity).toString()
                onQuantityChanged(product.price)

            }



        }
        holder.itemView.findViewById<CardView>(R.id.decrease_button).setOnClickListener {
            if (holder.productQuantity.text.toString().toInt() > 1){
            holder.apply {

                    this.productQuantity.text = this.productQuantity.text.toString()
                        .toInt()
                        .dec()
                        .toString()
                this.quantity--
                this.totalPriceTextView.text= product.price.times(quantity).toString()
                onQuantityChanged(-product.price)
            }

        }
        }


    }

}