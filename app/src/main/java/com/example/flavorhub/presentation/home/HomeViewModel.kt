package com.example.flavorhub.presentation.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavorhub.core.Resource
import com.example.flavorhub.domain.RestaurantRepo
import com.example.flavorhub.domain.entity.RestaurantEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(val repo: RestaurantRepo): ViewModel() {
    private var currentPage = 0

    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<HomeScreenEffect>()
    val effect: MutableSharedFlow<HomeScreenEffect> = _effect
    init{
        getRestaurantList()
    }
    fun sendEffect(effect: HomeScreenEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    fun OnIntent(intent: HomeIntent){
        when(intent) {
            HomeIntent.fetchRestaurants -> {
                getRestaurantList()
            }
            is HomeIntent.itemClicked -> {
                sendEffect(HomeScreenEffect.NavigateToRestaurantDetailsScreen(intent.id))
            }
        }
    }

    fun getRestaurantList(){
        viewModelScope.launch(Dispatchers.IO){
            try {
               repo.getRestaurants(currentPage++,createFilter(28.4795324, 76.9937197)).collectLatest {
                   when(it) {
                       is Resource.Error -> {}
                       is Resource.Loading ->{}
                       is Resource.Success<*> -> {
                           _state.value = _state.value.copy(restaurants = it.data as List<RestaurantEntity>)
                       }
                   }
               }
            } catch (e: Exception) {
            }
        }
    }

    fun getReviewList(){
        viewModelScope.launch(Dispatchers.IO){
            try {
                repo.getReviews().collectLatest {
                }
            } catch (e: Exception) {
            }
        }
    }
    fun createFilter(lat: Double, lon: Double, radius: Int = 20000): String {
        return "circle:$lon,$lat,$radius"
    }
}