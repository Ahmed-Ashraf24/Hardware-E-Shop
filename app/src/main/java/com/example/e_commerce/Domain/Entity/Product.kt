package com.example.e_commerce.Domain.Entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id:Int,
    val imageURL: String,
    val name: String,
    val price: Float,
    val category: String,
    val description:String,
    val rating: String
) : Parcelable