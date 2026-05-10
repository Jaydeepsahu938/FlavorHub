package com.example.flavorhub.core

import com.example.flavorhub.domain.dataModel.RestaurantModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("places")
    suspend fun getRestroList(
        @Query("categories") categories: String = "catering.restaurant",
        @Query("filter") filter: String,          // e.g., "circle:76.9937197,28.4795324,20000"
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
        @Query("apiKey") apiKey: String
    ): Response<RestaurantModel>
}