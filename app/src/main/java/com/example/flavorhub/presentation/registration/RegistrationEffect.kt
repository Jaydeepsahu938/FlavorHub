package com.example.flavorhub.presentation.registration

sealed class RegistrationEffect {
    object NavigateToLogin : RegistrationEffect()
    object NavigateToHome: RegistrationEffect()
    data class showError(val message: String) : RegistrationEffect()
}