package com.example.flavorhub.presentation.home

sealed class HomeIntent {
    data class itemClicked(val id: String): HomeIntent()
    object fetchRestaurants: HomeIntent()
}