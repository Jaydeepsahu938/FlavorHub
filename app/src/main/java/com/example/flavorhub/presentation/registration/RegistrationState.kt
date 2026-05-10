package com.example.flavorhub.presentation.registration

import com.example.flavorhub.presentation.AuthStateInterface

data class RegistrationState  (
    val name: String ="",
    val phoneNumber: String ="",
    val isRegistered: Boolean = false,
    override val isLoading: Boolean = false,
    override val email: String ="",
    override val password: String ="",
    override val error: String? = null
) : AuthStateInterface