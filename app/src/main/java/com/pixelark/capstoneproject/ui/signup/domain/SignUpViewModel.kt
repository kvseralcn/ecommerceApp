package com.pixelark.capstoneproject.ui.signup.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.pixelark.capstoneproject.core.BaseViewModel
import com.pixelark.capstoneproject.core.data.DataOrException
import com.pixelark.capstoneproject.core.data.UserModel
import com.pixelark.capstoneproject.core.repository.FirestoreRepository
import com.pixelark.capstoneproject.repository.auth.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthenticationRepository,
    private val firestoreRepository: FirestoreRepository,
    private val auth: FirebaseAuth
) :
    BaseViewModel() {

    companion object {
        const val TAG = "SignUpViewModel"
    }

    private val _signUpResult = MutableLiveData<DataOrException<Boolean, Exception>>()
    val signUpResult: LiveData<DataOrException<Boolean, Exception>> get() = _signUpResult

    fun signUp(email: String, password: String, name: String) {
        viewModelScope.launch {
            val result = authRepository.signUp(email, password)
            if (result.isSuccess) {
                val userId = auth.uid
                if (!userId.isNullOrEmpty()) {
                    firestoreRepository.createUser(
                        userModel = UserModel(
                            userId,
                            email,
                            name
                        ),
                        onError = { e ->
                            Log.e(TAG, e.message.toString())
                            _signUpResult.postValue(DataOrException(false, e))
                        },
                        onSuccess = {
                            _signUpResult.postValue(DataOrException(true))
                        }
                    )
                } else {
                    Log.e(TAG, "userId is null!")
                    _signUpResult.postValue(DataOrException(false, Exception("userId is null!")))
                }
            } else {
                when (result.exceptionOrNull()) {
                    is FirebaseAuthUserCollisionException -> {
                        _signUpResult.postValue(
                            DataOrException(
                                false,
                                Exception("mail adresi zaten kayıtlı")
                            )
                        )
                    }

                    else -> {
                        _signUpResult.postValue(
                            DataOrException(
                                false,
                                Exception("beklenmeyen bir hata oluştu")
                            )
                        )
                    }
                }
                Log.e(TAG, result.exceptionOrNull()?.message.toString())
            }
        }
    }
}