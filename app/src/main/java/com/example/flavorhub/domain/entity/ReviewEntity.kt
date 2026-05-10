package com.example.flavorhub.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "review_table")
data class ReviewEntity (
     @PrimaryKey val id : String,
     val restaurantId : String,
     val review : String,
     val name : String,
     val date : String,
     val rating:  String
)