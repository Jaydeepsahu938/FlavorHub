package com.example.flavorhub.presentation.welcome

sealed class WelcomeIntent() {
    object LoginClicked : WelcomeIntent()
    object RegisterClicked : WelcomeIntent()
}