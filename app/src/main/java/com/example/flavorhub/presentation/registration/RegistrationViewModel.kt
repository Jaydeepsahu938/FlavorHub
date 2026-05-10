package com.example.flavorhub.presentation.registration

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavorhub.core.Resource
import com.example.flavorhub.domain.AuthRepo
import com.example.flavorhub.domain.dataModel.UserModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RegistrationViewModel(val repo: AuthRepo) : ViewModel() {
   private var _state = mutableStateOf(RegistrationState())
    val state : MutableState<RegistrationState> = _state
   var _effect = MutableSharedFlow<RegistrationEffect>()
   val effect = _effect.asSharedFlow()

   fun onIntent(intent: RegistrationIntent){
       when(intent) {
           is RegistrationIntent.EmailChanged -> _state.value = state.value.copy(email = intent.value)
           is RegistrationIntent.NameChanged -> _state.value = state.value.copy(name = intent.value)
           is RegistrationIntent.PasswordChanged -> _state.value = state.value.copy(name = intent.value)
           is RegistrationIntent.PhoneChanged -> _state.value = state.value.copy(name = intent.value)
           RegistrationIntent.OnLoginClicked -> sendEffect(RegistrationEffect.NavigateToLogin)
           RegistrationIntent.OnRegisterClicked -> registerUser()
       }
   }

    private fun registerUser() {
        viewModelScope.launch {
           val user = UserModel(state.value.name,state.value.email,state.value.password,state.value.phoneNumber)
            repo.registerUser(user =user).collect{
                when(it) {
                    is Resource.Error -> {
                        sendEffect(RegistrationEffect.showError(it.message))
                    }
                    Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = true)
                    }
                    is Resource.Success<Boolean> -> {
                        _state.value = state.value.copy(isLoading = false , isRegistered = true)
                        sendEffect(RegistrationEffect.NavigateToHome)
                    }
                }
            }
        }
    }

    fun sendEffect(effect: RegistrationEffect){
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}