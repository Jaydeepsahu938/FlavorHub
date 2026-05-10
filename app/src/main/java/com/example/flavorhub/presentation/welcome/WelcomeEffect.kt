package com.example.flavorhub.presentation.welcome

sealed class WelcomeEffect {
    object NavigateToLogin : WelcomeEffect()
    object NavigateToRegister : WelcomeEffect()
}