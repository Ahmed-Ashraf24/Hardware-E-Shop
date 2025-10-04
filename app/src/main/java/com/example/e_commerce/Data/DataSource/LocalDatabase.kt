package com.example.e_commerce.Data.DataSource

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.e_commerce.Data.Mapper.ProductMapper
import com.example.e_commerce.Data.Model.DatabaseModel.AppDatabase
import com.example.e_commerce.Data.Model.DatabaseModel.CartItemEntity
import com.example.e_commerce.Data.Model.DatabaseModel.ProductEntity
import com.example.e_commerce.Data.Model.DatabaseModel.UserEntity
import com.example.e_commerce.Data.Model.DatabaseModel.UserProductRef
import com.example.e_commerce.Data.Model.DatabaseModel.UserWithProduct
import com.example.e_commerce.Domain.Entity.Product
import com.example.e_commerce.MyApplication
import com.example.e_commerce.Utilities.PasswordUtilities

class LocalDatabase : DatabaseClient {
    private val databaseInstance = AppDatabase.getDatabase(MyApplication.getAppContext())

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getUserFromEmailAndPassword(email: String, password: String): UserEntity? {
        val user = databaseInstance.userDao().getUserByEmail(email) ?: return null

        val hashedPassword = PasswordUtilities.hashPassword(password, user.passwordSalt)
        return user.takeIf { it.hashPassword == hashedPassword }

    }


    override suspend fun addUser(user: UserEntity) {
        databaseInstance.userDao().addUser(user)
    }

    override suspend fun editUserInfo(
        userId: Int,
        name: String,
        email: String,
        address: String?,
        phoneNumber: String?
    ) {
        databaseInstance.userDao().updateUserInfo(userId, name, email, address, phoneNumber)
    }

    override suspend fun changeUserPassword(
        userId: Int,
        userHashPassword: String,
        userPasswordSalt: String
    ) {
        databaseInstance.userDao().updateUserPassword(userId, userHashPassword, userPasswordSalt)
    }

    @SuppressLint("SuspiciousIndentation")
    override suspend fun getAllProducts(): List<ProductEntity> {
        return databaseInstance.productDao().getAllProducts()
    }

    override suspend fun addProduct(product: ProductEntity) {
        databaseInstance.productDao().addProduct(product)
    }

    override suspend fun addOrderedProduct(productId: Int, userId: Int) {
        databaseInstance.userProductRefDao()
            .insertCrossRef(UserProductRef(userId = userId, productId = productId))
    }

    override suspend fun addToCart(productId: Int, userId: Int, quantity: Int) {
        databaseInstance.cartDao().addOrUpdateCartItem(
            CartItemEntity(
                productId = productId,
                userId = userId,
                quantity = quantity
            )
        )
    }

    override suspend fun removeFromCart(productId: Int, userId: Int) {
        databaseInstance.cartDao().removeCartItem(userId, productId)
    }

    override suspend fun getCartProducts(userId: Int): List<ProductEntity> {
        return databaseInstance.cartDao().getCartProducts(userId)
    }

    override suspend fun getOrderedProducts(userId: Int): List<Product> {
        return databaseInstance.userProductRefDao()
            .getUserWithProducts(userId)
            .products
            .map(ProductMapper::toProduct)


    }

}