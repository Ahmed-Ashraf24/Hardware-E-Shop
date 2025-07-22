package com.example.e_commerce

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.e_commerce.Data.DataSource.LocalDatabase
import com.example.e_commerce.Data.Model.DatabaseModel.ProductEntity
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

class MyApplication : Application() {

    companion object {
        private lateinit var instance: MyApplication

        fun getAppContext() = instance.applicationContext
    }

    override fun onCreate() {
        val prefs: SharedPreferences = this.getSharedPreferences("dataStatus", Context.MODE_PRIVATE)

        instance = this
        if(!prefs.contains("DataSaved")){
        val products=getAllProductsFromCsv()
        products.forEach { product->
            runBlocking {
            LocalDatabase().addProduct(product)
                Log.d("products",product.toString()+"\n"+"DONE")

            }
        }
        prefs.edit().putBoolean("DataSaved",true).apply()
        }

        super.onCreate()

    }


    private fun getAllProductsFromCsv():List<ProductEntity>{
        Log.d("Product Function is called","22")
        val hardwareMap: HashMap<Int, String> = hashMapOf(
            R.raw.motherboarddata_with_images to "Motherboard",
            R.raw.cpudata_with_images to "CPU",
            R.raw.hdddata_with_images to "HDD",
            R.raw.ramdata_with_images to "RAM",
            R.raw.ssddata_with_images to "SSD",
            R.raw.casedata_with_images to "Case",
            R.raw.cpucoolerdata_with_images to "CPU Cooler",
            R.raw.gpudata_with_images to "GPU",
            R.raw.psudata_with_images to "PSU"
        )
        val products = mutableListOf<ProductEntity>()

        for((resource,type) in hardwareMap ){
        val inputStream = resources.openRawResource(resource)
        csvReader().open(inputStream) {
            readAllWithHeaderAsSequence().forEach { row ->

                val name = row["Name"] ?: return@forEach
                val priceText = row["Price"]?.replace(Regex("[^\\d.]"), "")?: "0"
                val price = priceText.toFloatOrNull() ?: 0f
                val imageUrl = row["Image URL"] ?: ""
                val brand = row["Producer"] ?: "Unknown Brand"

                val inStock = (row["Stock"] ?: "TRUE").equals("TRUE", ignoreCase = true)

                val description = row.entries
                    .filter { (key, _) -> key !in listOf("Name", "Price", "Producer", "Image URL", "Stock") }
                    .joinToString(separator = "\n") { (key, value) -> "$key: $value" }

                products.add(
                    ProductEntity(
                        name = name,
                        description = description,
                        price = price,
                        category = type,
                        imageUrl = imageUrl,
                        inStock = inStock,
                        brand = brand,
                        rating = 5f,
                        quantity = Random.nextInt()
                    )
                )
            }
        }
        }
        return products
    }
}