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
import com.example.e_commerce.Utilities.UIModule.CartItem

class CartAdapter(private var cartList: List<CartItem>,val onQuantityChanged:(Float)->(Unit) ,val onRemoveButtonClicked:(Int)->(Unit)) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
        val productCategory: TextView = itemView.findViewById(R.id.product_category)
        val productQuantity: TextView = itemView.findViewById(R.id.quantity_text)
        val totalPriceTextView: TextView = itemView.findViewById(R.id.subtotal_amount)
        val removeButton:ImageView=itemView.findViewById(R.id.delete_button)
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
        val product = cartList[position].product
        holder.setImage(product)
        holder.productName.text = product.name
        holder.productPrice.text = "${product.price} $"
        holder.productCategory.text = product.category
        holder.totalPriceValue = product.price
        holder.totalPriceTextView.text = "${holder.totalPriceValue} $"
       cartList[position].quantity = holder.productQuantity.text.toString().toInt()
        holder.itemView.findViewById<CardView>(R.id.increase_button).setOnClickListener {
            holder.apply {
                this.productQuantity.text = this.productQuantity.text.toString()
                    .toInt()
                    .inc()
                    .toString()
                cartList[position].quantity = cartList[position].quantity.inc()
                this.totalPriceTextView.text = product.price.times(cartList[position].quantity).toString()
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
                cartList[position].quantity = cartList[position].quantity.dec()
                this.totalPriceTextView.text= product.price.times(cartList[position].quantity).toString()
                onQuantityChanged(-product.price)
            }

        }

        }

        holder.removeButton.setOnClickListener {
            onRemoveButtonClicked(product.id)
        }
    }
    fun  getCartListProducts():List<CartItem>{
        return cartList
    }

}