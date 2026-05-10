package com.example.flavorhub.presentation.login

import com.example.flavorhub.presentation.registration.RegistrationIntent

sealed class LoginIntent {
    object OnLoginClicked : LoginIntent()
    object OnRegisterClicked : LoginIntent()
    data class EmailChanged(val value: String) : LoginIntent()
    data class PasswordChanged(val value: String) : LoginIntent()
}