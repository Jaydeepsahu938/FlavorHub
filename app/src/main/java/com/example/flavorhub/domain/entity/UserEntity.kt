package com.example.flavorhub.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    val name: String,
    @PrimaryKey
    val email: String,
    val password: String,
    val phoneNumber: String,
)