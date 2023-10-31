package com.pixelark.capstoneproject.core.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.pixelark.capstoneproject.core.data.UserModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    fun createUser(
        userModel: UserModel,
        onError: (Exception) -> Unit,
        onSuccess: () -> Unit
    ) {
        firestore.collection("users").document(userModel.id)
            .set(userModel)
            .addOnSuccessListener {
                Log.d("FirestoreOldu", "Firestore kaydettim")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreHata", "Firestore kayıt hatası", e)
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
                        onError(Exception("Belge verisi çözümlenemedi."))
                    }
                } else {
                    onError(Exception("Belge bulunamadı."))
                }
            }
            .addOnFailureListener { e ->
                onError(e)
            }
    }
}