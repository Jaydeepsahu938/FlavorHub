package com.example.flavorhub.presentation.home

import com.example.flavorhub.domain.entity.RestaurantEntity

data class HomeScreenState(
    val restaurants: List<RestaurantEntity> = emptyList(),

    val isLoading: Boolean = false,

    val error: String? = null
)