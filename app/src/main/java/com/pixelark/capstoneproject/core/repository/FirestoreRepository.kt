package com.pixelark.capstoneproject.core.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.pixelark.capstoneproject.core.data.UserModel
import com.pixelark.capstoneproject.repository.auth.AuthenticationRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FirestoreRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseUser: FirebaseAuth,
    private val authenticationRepository: AuthenticationRepository
) {
    fun createUser(
        userModel: UserModel,
        onError: (Exception) -> Unit,
        onSuccess: () -> Unit
    ) {
        firestore.collection("users").document(userModel.id)
            .set(userModel)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onError(e)
            }
    }

    fun readData(
        userId: String,
        onSuccess: (UserModel) -> Unit,
        onError: (Exception) -> Unit
    ) {
        firestore.collection("users").document(userId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val userModel = documentSnapshot.toObject(UserModel::class.java)
                    if (userModel != null) {
                        onSuccess(userModel)
                    } else {
                        onError(Exception())
                    }
                } else {
                    onError(Exception())
                }
            }
            .addOnFailureListener { e ->
                onError(e)
            }
    }

    fun updateUser(
        userMap: HashMap<String, Any>,
        onError: (Exception) -> Unit,
        onSuccess: () -> Unit
    ) {
        val userId = firebaseUser.currentUser?.uid!!
        val userRef = firestore.collection("users").document(userId)
        userRef
            .update(userMap)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onError(e)
            }
    }
}