package com.example.flavorhub.presentation.home

sealed class HomeScreenEffect {
    data class NavigateToRestaurantDetailsScreen(val id : String) : HomeScreenEffect()
    data class showError(val message: String) : HomeScreenEffect()
}