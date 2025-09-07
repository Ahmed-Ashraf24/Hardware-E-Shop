package com.example.e_commerce.Utilities.UiAdapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.example.e_commerce.Utilities.UIModule.Category

class CategoriesAdapter(
    private var categories: List<Category>,
    private val onCategoryClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item_layout, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size



    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoryCard: CardView = itemView.findViewById(R.id.category_card)
        private val categoryIcon: ImageView = itemView.findViewById(R.id.category_icon)
        private val categoryName: TextView = itemView.findViewById(R.id.category_name)

        fun bind(category: Category) {
            categoryName.text = category.name
            categoryIcon.setImageResource(category.iconRes)

            try {
                categoryCard.setCardBackgroundColor(Color.parseColor(category.backgroundColor))
            } catch (e: IllegalArgumentException) {
                categoryCard.setCardBackgroundColor(Color.parseColor("#E8F5E8"))
            }



            itemView.setOnClickListener {
                onCategoryClick(category)
            }
        }
    }
}