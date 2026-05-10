package com.example.flavorhub.data

import com.example.flavorhub.core.Resource
import com.example.flavorhub.domain.AuthRepo
import com.example.flavorhub.domain.entity.UserEntity
import com.example.flavorhub.domain.dataModel.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepoImpl(val userDao: UserDao) : AuthRepo {

    override fun registerUser(user: UserModel): Flow<Resource<Boolean>> = flow {
        try {
            userDao.insertUser(userEntity = UserEntity(
                name = user.name,
                email = user.email,
                password = user.password,
                phoneNumber = user.phoneNumber))
            emit(Resource.Success(true))
        } catch (e: Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }

    override fun loginUser(
        email: String,
        password: String
    ): Flow<Resource<Boolean>> = flow {
        val user = userDao.getUserByEmail(email)
        if(user?.password.equals(password))
        {
            emit(Resource.Success(true))
        }
        else
        {
            emit(Resource.Error("Invalid Credentials"))
        }
    }
}