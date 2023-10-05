package com.pixelark.capstoneproject.ui.signup.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pixelark.capstoneproject.core.BaseViewModel
import com.pixelark.capstoneproject.repository.auth.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val authRepository: AuthenticationRepository) :
    BaseViewModel() {

    private val _authResult = MutableLiveData<Result<Unit>>()
    val authResult: LiveData<Result<Unit>> get() = _authResult

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _authResult.value = authRepository.signUp(email, password)
        }
    }
}