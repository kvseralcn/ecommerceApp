package com.pixelark.capstoneproject.ui.forgotpassword.domain

import com.pixelark.capstoneproject.core.BaseViewModel
import com.pixelark.capstoneproject.repository.auth.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val authRepository: AuthenticationRepository) :
    BaseViewModel() {
}