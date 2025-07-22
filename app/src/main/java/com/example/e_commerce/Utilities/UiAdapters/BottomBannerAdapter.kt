package com.example.e_commerce.Utilities.UiAdapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.Utilities.UIModule.BottomBanner
import com.example.e_commerce.R

class BottomBannerAdapter(private val bottomBanners: List<BottomBanner>) :
    RecyclerView.Adapter<BottomBannerAdapter.BottomBannerViewHolder>() {

    class BottomBannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val adImage: ImageView = itemView.findViewById(R.id.ad_image)
        val adText: TextView = itemView.findViewById(R.id.ad_text)
        val shopNowButton: Button = itemView.findViewById(R.id.shop_now_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomBannerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bottom_banner, parent, false)
        return BottomBannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BottomBannerViewHolder, position: Int) {
        val bottomBanner = bottomBanners[position]
        holder.adImage.setImageResource(bottomBanner.imageResId)
        holder.adText.text = bottomBanner.text
        holder.shopNowButton.setOnClickListener {

        }
    }

    override fun getItemCount(): Int = bottomBanners.size
}