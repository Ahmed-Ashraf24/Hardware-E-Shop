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

class ProductAdapter(private val products: List<Product>,val onItemClicked :(Product)->(Unit)) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
        val productRating: TextView = itemView.findViewById(R.id.product_rating)
        fun setImage(product: Product){
            Glide.with(itemView.context)
                .load(product.imageURL)
                .into(productImage)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.setImage(product)
        holder.productName.text = product.name
        holder.productPrice.text = "${product.price} $"
        holder.productRating.text = product.rating
        holder.itemView.setOnClickListener {
            onItemClicked(product)
        }
    }

    override fun getItemCount(): Int = products.size
}