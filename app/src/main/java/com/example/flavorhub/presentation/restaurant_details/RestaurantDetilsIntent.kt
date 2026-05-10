package com.example.flavorhub.presentation.restaurant_details

import com.example.flavorhub.domain.entity.RestaurantEntity

sealed class RestaurantDetailsIntent {
   data class  onThumbDownClicked(val restaurant: RestaurantEntity) : RestaurantDetailsIntent()
   data class fetchRestaurant(val id: String) : RestaurantDetailsIntent()
}