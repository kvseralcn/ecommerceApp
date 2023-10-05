package com.pixelark.capstoneproject

import com.pixelark.capstoneproject.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() :
    BaseViewModel()
//@Inject constructor(private val authRepository: AuthenticationRepository) : ViewModel()
{

    //  private val _authResult = MutableLiveData<Result<Unit>>()
    //  val authResult: LiveData<Result<Unit>> get() = _authResult

    //  fun signUp(email: String, password: String) {
    //      viewModelScope.launch {
    //          _authResult.value = authRepository.signUp(email, password)
    //      }
    //  }

    //  fun signIn(email: String, password: String) {
    //      viewModelScope.launch {
    //          _authResult.value = authRepository.signIn(email, password)
    //      }
    //  }
}