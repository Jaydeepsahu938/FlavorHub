package com.example.flavorhub.presentation.restaurant_details

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavorhub.core.Resource
import com.example.flavorhub.domain.RestaurantRepo
import com.example.flavorhub.domain.entity.RestaurantEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RestaurantDetailsViewModel(val repo: RestaurantRepo) : ViewModel() {
    private val _state = MutableStateFlow(RestaurantDetailsState())
    val state = _state.asStateFlow()

    fun onIntent(intent: RestaurantDetailsIntent){
        when(intent) {
            is RestaurantDetailsIntent.onThumbDownClicked -> {
                updateThumbDown(intent.restaurant)
                _state.value = _state.value.copy(restaurants = intent.restaurant)
            }

            is RestaurantDetailsIntent.fetchRestaurant -> {
                getRestaurant(intent.id)
            }
        }
    }
    fun getRestaurant(id: String){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getRestaurantWithId(id).collect{
                when(it) {
                    is Resource.Error -> _state.value = _state.value.copy(isLoading = false, error = it.message)
                    Resource.Loading -> _state.value = _state.value.copy(isLoading = true)
                    is Resource.Success<*> -> _state.value = _state.value.copy(isLoading = false, restaurants = it.data as RestaurantEntity)
                }
            }
        }
    }

   fun updateThumbDown(restaurant: RestaurantEntity){
       viewModelScope.launch(Dispatchers.IO) {
           repo.updateRestaurant(restaurant)
       }
   }
}