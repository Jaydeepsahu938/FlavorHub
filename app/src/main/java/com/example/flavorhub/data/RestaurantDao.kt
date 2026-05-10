package com.example.flavorhub.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.flavorhub.domain.entity.RestaurantEntity

@Dao
interface RestaurantDao {
    @Query("SELECT * FROM restaurant_table WHERE isHidden = 0")
    fun getAllRestaurant(): List<RestaurantEntity>

    @Query("SELECT * FROM restaurant_table WHERE id = :id")
    fun getRestaurants(id: String): RestaurantEntity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurant(restro: List<RestaurantEntity>)

    @Update
    suspend fun updateRestaurant(restro: RestaurantEntity)

    @Query("SELECT * FROM restaurant_table WHERE isHidden = 1")
    fun getHiddenRestaurant(): List<RestaurantEntity>
}