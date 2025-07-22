package com.example.e_commerce.Utilities

import java.security.MessageDigest

object Constants {


    private val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+.com".toRegex()
    fun isValidEmail(email:String ) =EMAIL_REGEX.matches(email)

    fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}