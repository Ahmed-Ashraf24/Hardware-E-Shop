package com.example.e_commerce.Data.Model.DatabaseModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Product")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id:Int =0 ,
    val name :String ,
    val description :String ,
    val price :Float ,
    val category :String ,
    val quantity:Int,
    val imageUrl :String ,
    val inStock :Boolean ,
    val brand :String ,
    val rating :Float ,
    val createdAt : Long = System.currentTimeMillis()
)
