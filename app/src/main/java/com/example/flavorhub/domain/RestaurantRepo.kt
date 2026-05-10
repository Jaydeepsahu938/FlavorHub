package com.example.flavorhub.domain

import com.example.flavorhub.core.Resource
import com.example.flavorhub.domain.dataModel.RestaurantModel
import com.example.flavorhub.domain.dataModel.ReviewModel
import com.example.flavorhub.domain.entity.RestaurantEntity
import kotlinx.coroutines.flow.Flow

interface RestaurantRepo {

    suspend fun getRestaurantWithId(id : String): Flow<Resource<RestaurantEntity>>
    suspend fun getRestaurants(page: Int,filter: String): Flow<Resource<List<RestaurantEntity>>>
    suspend fun getHiddenRestaurants(): Flow<Resource<List<RestaurantEntity>>>
    suspend fun getReviews(): Flow<Resource<List<ReviewModel>>>

    suspend fun updateRestaurant(restro: RestaurantEntity)
}