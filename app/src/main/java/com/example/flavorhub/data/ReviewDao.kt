package com.example.flavorhub.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flavorhub.domain.entity.ReviewEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun getAllReviews(review: ReviewEntity)

    @Query("SELECT * FROM review_table WHERE restaurantId = :restaurantId")
    fun getReviews(restaurantId : String): Flow<List<ReviewEntity>>
}