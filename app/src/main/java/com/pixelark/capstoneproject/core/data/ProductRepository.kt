package com.pixelark.capstoneproject.core.data

import com.pixelark.capstoneproject.core.service.ProductDao
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productDao: ProductDao) {
    fun getFavoriteProducts(): List<ProductModel> {
        return productDao.getFavoriteProducts()
    }

    fun insertProduct(product: ProductModel) {
        productDao.insertProduct(product)
    }

    fun deleteProduct(productId: Int) {
        productDao.deleteProduct(productId)
    }
}
