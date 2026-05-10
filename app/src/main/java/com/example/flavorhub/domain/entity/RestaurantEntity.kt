package com.example.flavorhub.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurant_table")
data class RestaurantEntity (
    val name: String,
    @PrimaryKey
    val id :String,
    val locality: String,
    val dishes: String,
    val rating: String,
    val image: String,
    val isHidden: Boolean = false,
)