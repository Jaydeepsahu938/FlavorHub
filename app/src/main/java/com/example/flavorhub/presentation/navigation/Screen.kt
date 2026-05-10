package com.example.flavorhub.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object Welcome: NavKey
@Serializable
data object Registration: NavKey
@Serializable
data object Login: NavKey
@Serializable
data object Home: NavKey
@Serializable
data class RestaurantDetail(val id: String): NavKey
@Serializable
data object HiddenRestaurant: NavKey