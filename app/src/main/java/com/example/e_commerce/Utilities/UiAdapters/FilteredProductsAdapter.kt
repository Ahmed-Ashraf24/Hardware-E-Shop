package com.example.e_commerce.Utilities.UiAdapters

import android.content.res.ColorStateList
import android.graphics.Color
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

class FilteredProductsAdapter(private val filteredProductList: List<Product>,  private val onAddToCartClick: (Product) -> Unit):
    RecyclerView.Adapter<FilteredProductsAdapter.FilteredProductsViewHolder>() {
   inner class FilteredProductsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.product_image)
        private val name: TextView = itemView.findViewById(R.id.product_name)
        private val brand: TextView = itemView.findViewById(R.id.brand_name)
        private val specs: TextView = itemView.findViewById(R.id.specifications)
        private val features: TextView = itemView.findViewById(R.id.features)
        private val price: TextView = itemView.findViewById(R.id.current_price)
        private val rating: TextView = itemView.findViewById(R.id.rating_text)
        private val reviews: TextView = itemView.findViewById(R.id.reviews_count)
        private val stockText: TextView = itemView.findViewById(R.id.stock_text)
        private val stockIndicator: View = itemView.findViewById(R.id.stock_indicator)
        private val addToCartBtn: CardView = itemView.findViewById(R.id.add_to_cart_button)
        fun bind(product: Product) {

            Glide.with(image.context)
                .load(product.imageURL)
                .into(image)

            name.text = product.name
            brand.text = product.category
            specs.text = product.description
            features.text = "• Great quality • Fast delivery"

            rating.text = product.rating
            reviews.text = "(100+ reviews)"

            price.text = "$${product.price}"


            stockIndicator.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#4CAF50"))
            stockText.setTextColor(Color.parseColor("#4CAF50"))
            stockText.text = "In Stock"

            addToCartBtn.setOnClickListener { onAddToCartClick(product) }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilteredProductsAdapter.FilteredProductsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.products_item, parent, false)
        return FilteredProductsViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: FilteredProductsAdapter.FilteredProductsViewHolder,
        position: Int
    ) {
        holder.bind(filteredProductList[position])
    }

    override fun getItemCount(): Int=filteredProductList.size
}