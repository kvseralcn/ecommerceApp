package com.pixelark.capstoneproject.ui.user.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.pixelark.capstoneproject.core.BaseViewModel
import com.pixelark.capstoneproject.core.data.DataOrException
import com.pixelark.capstoneproject.core.data.UserModel
import com.pixelark.capstoneproject.core.repository.FirestoreRepository
import com.pixelark.capstoneproject.core.repository.StoreRepository
import com.pixelark.capstoneproject.ui.signup.domain.SignUpViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val storeRepository: StoreRepository,
    private val firestoreRepository: FirestoreRepository,
    private val auth: FirebaseAuth
) :
    BaseViewModel() {
    private val _signUpResult = MutableLiveData<DataOrException<Boolean, Exception>>()
    val signUpResult: LiveData<DataOrException<Boolean, Exception>> get() = _signUpResult

    private val _userData = MutableLiveData<UserModel>()
    val userData: LiveData<UserModel> get() = _userData
    fun signOut() {
        viewModelScope.launch {
            val userId = auth.uid
            if (!userId.isNullOrEmpty()) {
                auth.signOut()
            }
        }
    }

    fun readData() {
        viewModelScope.launch {
            val userId = auth.uid
            if (!userId.isNullOrEmpty()) {
                firestoreRepository.readData(
                    userId,
                    onError = { e ->
                        Log.e(SignUpViewModel.TAG, e.message.toString())
                        _signUpResult.postValue(DataOrException(false, e))
                    },
                    onSuccess = { userModel ->
                        _userData.postValue(userModel)
                        _signUpResult.postValue(DataOrException(true))
                    }
                )
            }
        }
    }

}