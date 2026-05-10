package com.example.flavorhub.presentation.login

import com.example.flavorhub.presentation.AuthStateInterface

data class LoginState(
    override val email: String ="",
    override val password: String ="",
    override val isLoading: Boolean = false,
    override val error: String? = null,
    val isLoggedIn: Boolean = false
): AuthStateInterface