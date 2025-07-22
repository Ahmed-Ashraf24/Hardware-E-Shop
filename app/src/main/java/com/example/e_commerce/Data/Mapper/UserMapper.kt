package com.example.e_commerce.Data.Mapper

import com.example.e_commerce.Data.Model.DatabaseModel.UserEntity
import com.example.e_commerce.Domain.Entity.User

class UserMapper {
    companion object{
        fun toUser(user: UserEntity):User{
            return User(name = user.name,
                id=user.id,
                email = user.email,
                phone = user.phoneNumber!!,
                address = user.address!!)
        }
    }
}