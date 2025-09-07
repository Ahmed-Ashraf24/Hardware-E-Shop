package com.example.e_commerce.Utilities.UIModule

data class Category(
    val id: String,
    val name: String,
    val iconRes: Int,
    val backgroundColor: String,
    val isSelected: Boolean = false
)
