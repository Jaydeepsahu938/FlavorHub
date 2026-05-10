package com.example.flavorhub.presentation.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flavorhub.core.Resource
import com.example.flavorhub.domain.AuthRepo
import com.example.flavorhub.presentation.navigation.Login
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel(val repo: AuthRepo): ViewModel() {

    private val _state = mutableStateOf(LoginState())
    val state: MutableState<LoginState>  = _state

    private val _effect = MutableSharedFlow<LoginEffect>()
    val effect = _effect.asSharedFlow()


    fun onIntent(intent: LoginIntent) {
        when(intent) {
            is LoginIntent.EmailChanged -> _state.value = state.value.copy(email = intent.value)
            LoginIntent.OnLoginClicked -> login()
            LoginIntent.OnRegisterClicked -> sendEffect(LoginEffect.NavigateToRegister)
            is LoginIntent.PasswordChanged -> _state.value = state.value.copy(password = intent.value)
            else -> {}
        }
    }

    fun sendEffect(effect: LoginEffect){
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
    fun login(){
        viewModelScope.launch {
            repo.loginUser(state.value.email,state.value.password).collect {
                when(it) {
                    is Resource.Error -> {
                        sendEffect(LoginEffect.showError(it.message))
                    }
                    Resource.Loading -> {

                    }
                    is Resource.Success<*> -> {
                        sendEffect(LoginEffect.NavigateToHome)
                    }
                }
            }
        }
    }
}