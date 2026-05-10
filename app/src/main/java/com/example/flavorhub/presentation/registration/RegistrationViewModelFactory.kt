package com.example.flavorhub.presentation.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flavorhub.domain.AuthRepo

class RegistrationViewModelFactory(
    private val repo: AuthRepo
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegistrationViewModel(repo) as T
    }
}