package com.example.e_commerce.Utilities.UiAdapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.Utilities.UIModule.Banner
import com.example.e_commerce.R

class BannerAdapter(private val banners: List<Banner>) :
    RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bannerText: TextView = itemView.findViewById(R.id.banner_subtitle)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_banner, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val banner = banners[position]
        holder.bannerText.text = banner.text

    }

    override fun getItemCount(): Int = banners.size
}