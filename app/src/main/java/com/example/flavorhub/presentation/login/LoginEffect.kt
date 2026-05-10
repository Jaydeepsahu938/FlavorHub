package com.example.flavorhub.presentation.login

import com.example.flavorhub.presentation.registration.RegistrationEffect

sealed class LoginEffect {
    object NavigateToRegister : LoginEffect()
    object NavigateToHome: LoginEffect()
    data class showError(val message: String) : LoginEffect()
}