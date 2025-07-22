package com.example.e_commerce.Presntation.Screens.Auth

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.example.e_commerce.Data.Model.DatabaseModel.UserEntity
import com.example.e_commerce.Presntation.ViewModel.SignUpViewModel
import com.example.e_commerce.R
import com.example.e_commerce.Utilities.PasswordUtilities
import com.example.e_commerce.databinding.ActivityRegisterBinding
import kotlinx.coroutines.runBlocking

class RegisterActivity : AppCompatActivity() {
    lateinit var binding :ActivityRegisterBinding
    private var selectedGender: String? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnMale.isEnabled=true
        binding.btnFemale.isEnabled= true
        setupListeners()
    }
    val signUpViewModel=SignUpViewModel()
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupListeners() {
        // Add text change listeners to all fields
        with(binding) {
            val inputs = listOf(etName, etEmail, etPassword, etConfirmPassword, etPhone, etAddress)

            for (input in inputs) {
                input.addTextChangedListener { checkFields() }
            }

            btnMale.setOnClickListener {
                selectedGender = "Male"
                highlightGenderSelection(true)
            }

            btnFemale.setOnClickListener {
                selectedGender = "Female"
                highlightGenderSelection(false)
            }

            // Continue button
            btnContinue.setOnClickListener {
                if (selectedGender == null) {
                    Toast.makeText(this@RegisterActivity, "Please select gender", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (areAllFieldsValid()) {
                    runBlocking {
                    registerUser()}
                } else {
                    Toast.makeText(this@RegisterActivity, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }

            // Disable gender buttons initially
            btnMale.isEnabled = false
            btnFemale.isEnabled = false
        }
    }

    private fun checkFields() {
        with(binding) {
            val fieldsNotEmpty = listOf(
                etName.text,
                etEmail.text,
                etPassword.text,
                etConfirmPassword.text,
                etPhone.text,
                etAddress.text
            ).all { it?.isNotEmpty() == true }

            // Enable gender buttons if all fields filled
            btnMale.isEnabled = fieldsNotEmpty
            btnFemale.isEnabled = fieldsNotEmpty
        }
    }

    private fun areAllFieldsValid(): Boolean {
        with(binding) {
            return listOf(
                etName.text,
                etEmail.text,
                etPassword.text,
                etConfirmPassword.text,
                etPhone.text,
                etAddress.text
            ).all { it?.isNotEmpty() == true } && selectedGender != null
        }
    }

    private fun highlightGenderSelection(isMale: Boolean) {
        with(binding) {
            if (isMale) {

                btnFemale.isEnabled=false
            } else {
                btnMale.isEnabled=false
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun registerUser() {
        with(binding) {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()
            val phone = etPhone.text.toString()
            val address = etAddress.text.toString()

            if (password != confirmPassword) {
                Toast.makeText(this@RegisterActivity, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return
            }

            // Proceed with registration logic
            Toast.makeText(this@RegisterActivity, "Registering $name as $selectedGender", Toast.LENGTH_LONG).show()
            val salt=PasswordUtilities.generateSalt()
            val hashedPassword=PasswordUtilities.hashPassword(password,salt)
            signUpViewModel.register(
                UserEntity(
                name = name,
                email = email,
                gender = selectedGender!!,
                hashPassword = hashedPassword,
                passwordSalt = salt,
                profileImage = "",
                phoneNumber = if (phone.isNotEmpty()) phone else null,
                address = if (address.isNotEmpty()) address else null
            )
            )

            signUpViewModel.registerState.value.let {
                result ->
                result!!.fold(
                    onSuccess = {
                        val intent=Intent(this@RegisterActivity, RegisterActivity::class.java)
                        startActivity(intent)
                    },
                    onFailure = {
                        exception ->
                        Log.d("exception appears during register",exception.toString())
                        Toast.makeText(this@RegisterActivity,exception.toString(),Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}
