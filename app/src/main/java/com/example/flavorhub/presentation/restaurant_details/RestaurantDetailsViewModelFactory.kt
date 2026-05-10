package com.example.flavorhub.presentation.restaurant_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flavorhub.domain.RestaurantRepo
import com.example.flavorhub.presentation.home.HomeViewModel

class RestaurantDetailsViewModelFactory(
    private val repo: RestaurantRepo
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RestaurantDetailsViewModel(repo) as T
    }
}