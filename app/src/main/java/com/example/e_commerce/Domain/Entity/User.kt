package com.example.e_commerce.Domain.Entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val address:String,
    val phone:String,
    val gender:String

) : Parcelable