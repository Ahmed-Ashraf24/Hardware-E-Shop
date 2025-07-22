package com.example.e_commerce.Data.Model.DatabaseModel

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(entities = [UserEntity::class, ProductEntity::class, UserProductRef::class, CartItemEntity::class], version = 4)
abstract class AppDatabase :RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
    abstract fun userProductRefDao(): UserWithProductsDao
    abstract fun cartDao(): CartDao
    companion object{
        @Volatile
        private var INSTANCE : AppDatabase?=null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val passphrase: ByteArray = SQLiteDatabase.getBytes("YourSecurePassphrase".toCharArray())
                val factory = SupportFactory(passphrase)

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ecommerce_database"
                ).allowMainThreadQueries()
                    .openHelperFactory(factory)
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }


}