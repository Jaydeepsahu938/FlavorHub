package com.example.flavorhub.presentation.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class WelcomeViewModel : ViewModel() {
    private val _effect = MutableSharedFlow<WelcomeEffect>()
    val effect = _effect.asSharedFlow()

    fun onIntent(intent: WelcomeIntent){
        when(intent) {
            WelcomeIntent.LoginClicked -> sendEffect(WelcomeEffect.NavigateToLogin)
            WelcomeIntent.RegisterClicked -> sendEffect(WelcomeEffect.NavigateToRegister)
        }
    }
    fun sendEffect(effect: WelcomeEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}