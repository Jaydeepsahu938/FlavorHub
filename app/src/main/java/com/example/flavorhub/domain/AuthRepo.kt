package com.example.flavorhub.domain

import com.example.flavorhub.core.Resource
import com.example.flavorhub.domain.dataModel.UserModel
import kotlinx.coroutines.flow.Flow

interface AuthRepo {
    fun registerUser(user: UserModel): Flow<Resource<Boolean>>
    fun loginUser(email: String, password: String): Flow<Resource<Boolean>>
}