package com.hardwarestore.presentation.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commerce.Domain.Entity.Product
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ProductsItemBinding

class ProductSearchAdapter(
    private val onItemClick: (Product) -> Unit,
    private val onAddToCartClick: (Product) -> Unit,
    private val onFavoriteClick: (Product) -> Unit
) : ListAdapter<Product, ProductSearchAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductViewHolder(
        private val binding: ProductsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(position))
                }
            }

            binding.addToCartButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onAddToCartClick(getItem(position))
                }
            }

            binding.favoriteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onFavoriteClick(getItem(position))
                }
            }
        }

        fun bind(product: Product) {
            binding.apply {
                // Product image
                Glide.with(itemView.context)
                    .load(product.imageURL)
                    .centerCrop()
                    .into(productImage)

                // Basic info
                brandName.text = product.category // Using category like FilteredProductsAdapter
                productName.text = product.name
                specifications.text = product.description

                // Static features like FilteredProductsAdapter
                features.text = "• Great quality • Fast delivery"

                // Static rating and reviews like FilteredProductsAdapter
                ratingText.text = product.rating
                reviewsCount.text = "(100+ reviews)"

                // Price formatting to match FilteredProductsAdapter
                currentPrice.text = "$${product.price}"

                // Hide discount elements (keeping static approach)
                originalPrice.visibility = View.GONE
                discountBadge.visibility = View.GONE

                // Static stock status like FilteredProductsAdapter
                stockIndicator.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#4CAF50"))
                stockText.setTextColor(Color.parseColor("#4CAF50"))
                stockText.text = "In Stock"
            }
        }
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}

// Utility functions for formatting (keeping for backward compatibility)
fun formatPrice(price: Double): String {
    return "$%.2f".format(price)
}

fun formatRating(rating: Double): String {
    return "%.1f".format(rating)
}