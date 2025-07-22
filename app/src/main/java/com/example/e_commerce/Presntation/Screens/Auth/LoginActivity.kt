package com.example.e_commerce.Presntation.Screens.Auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.e_commerce.Presntation.Screens.MainScreen.MainScreen
import com.example.e_commerce.Presntation.ViewModel.LoginViewModel
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding:ActivityLoginBinding
    val loginViewModel=LoginViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSignIn.setOnClickListener {
            if(!(binding.etEmail.text!!.isBlank() && binding.etPassword.text!!.isBlank())){
                loginViewModel.login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
              loginViewModel.loginState.value.let {
                  result ->
                  result!!.fold(
                      onSuccess = {user->
                          val intent= Intent(this@LoginActivity, MainScreen::class.java).apply {
                              putExtra("user",user)
                          }
                          startActivity(intent)
                      },
                      onFailure = {exception ->
                          Toast.makeText(this,exception.message,Toast.LENGTH_SHORT).show()
                      }
                  )
              }
            }
            else{
                Toast.makeText(this,"you must fill all the fields ",Toast.LENGTH_SHORT).show()
            }
        }
        binding.tvRegister.setOnClickListener {
            val intent=Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}