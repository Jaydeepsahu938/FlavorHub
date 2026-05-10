package com.example.flavorhub.presentation.registration

sealed class RegistrationIntent {
    object OnLoginClicked : RegistrationIntent()
    object OnRegisterClicked : RegistrationIntent()
    data class NameChanged(val value: String) : RegistrationIntent()
    data class EmailChanged(val value: String) : RegistrationIntent()
    data class PhoneChanged(val value: String) : RegistrationIntent()
    data class PasswordChanged(val value: String) : RegistrationIntent()
}