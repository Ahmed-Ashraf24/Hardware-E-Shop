package com.example.e_commerce.Utilities

import android.os.Build
import androidx.annotation.RequiresApi
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

object PasswordUtilities {


        @RequiresApi(Build.VERSION_CODES.O)
        fun generateSalt(length: Int = 16): String {
            val random = SecureRandom()
            val salt = ByteArray(length)
            random.nextBytes(salt)
            return Base64.getEncoder().encodeToString(salt)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun hashPassword(password: String, salt: String): String {
            val keySpec = PBEKeySpec(password.toCharArray(), Base64.getDecoder().decode(salt), 10000, 256)
            val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
            val hash = factory.generateSecret(keySpec).encoded
            return Base64.getEncoder().encodeToString(hash)
        }

}