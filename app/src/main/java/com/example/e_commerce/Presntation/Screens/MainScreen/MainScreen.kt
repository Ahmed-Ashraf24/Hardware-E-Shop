package com.example.e_commerce.Presntation.Screens.MainScreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.e_commerce.Domain.Entity.Product
import com.example.e_commerce.Domain.Entity.User
import com.example.e_commerce.Presntation.Screens.MainScreen.CartScreen.CartFragment
import com.example.e_commerce.Presntation.Screens.MainScreen.HomeScreen.HomePage.MainScreenFragment
import com.example.e_commerce.Presntation.Screens.MainScreen.OrderScreen.OrderFragment
import com.example.e_commerce.Presntation.Screens.MainScreen.ProfileScreen.AcountFragment
import com.example.e_commerce.Presntation.ViewModel.CartViewModel
import com.example.e_commerce.Presntation.ViewModel.OrderViewModel
import com.example.e_commerce.Presntation.ViewModel.ProductsViwModel
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ActivityMainScreenBinding

class MainScreen : AppCompatActivity() {
    lateinit var binding: ActivityMainScreenBinding
    val productsViwModel=ProductsViwModel()
    var product: Product?=null
    val cartViewModel= CartViewModel()
    var user:User?=null
    val orderViewModel=OrderViewModel()
    var selectedCategory=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(savedInstanceState==null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MainScreenFragment())
                .commit()
        }
        user=intent.getParcelableExtra("user")
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val selectedFragment: Fragment = when (item.itemId) {
                R.id.nav_home -> MainScreenFragment()
                R.id.nav_order -> OrderFragment()
                R.id.nav_account -> AcountFragment()
                R.id.nav_wishlist -> CartFragment()
                else -> MainScreenFragment()
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, selectedFragment)
                .commit()
            true
        }
        productsViwModel.getAllProducts()

    }

}