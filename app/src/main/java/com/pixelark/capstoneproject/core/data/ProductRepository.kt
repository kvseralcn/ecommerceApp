package com.pixelark.capstoneproject.core.data

import com.google.firebase.auth.FirebaseAuth
import com.pixelark.capstoneproject.core.service.ProductDao
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productDao: ProductDao,
    private val firebaseAuth: FirebaseAuth
) {
    fun getFavoriteProducts(): List<ProductModel> {
        return productDao.getFavoriteProducts(firebaseAuth.currentUser?.uid.orEmpty())
    }

    fun insertProduct(product: ProductModel) {
        productDao.insertProduct(product.copy(userId = firebaseAuth.currentUser?.uid))
    }

    fun deleteProduct(productId: Int) {
        productDao.deleteProduct(productId)
    }
}
