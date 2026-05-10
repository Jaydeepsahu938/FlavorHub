package com.example.flavorhub.presentation.restaurant_details

import com.example.flavorhub.domain.entity.RestaurantEntity

data class RestaurantDetailsState(
    val restaurants: RestaurantEntity? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)