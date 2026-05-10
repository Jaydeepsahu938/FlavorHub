package com.example.flavorhub.presentation

interface AuthStateInterface {
    val email: String

    val password: String
    val isLoading: Boolean

    val error: String?
}