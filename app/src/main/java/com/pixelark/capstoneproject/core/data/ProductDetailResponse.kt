package com.pixelark.capstoneproject.core.data

data class ProductDetailResponse(
    val product: ProductModel,
    val status: Int,
    val message: String
)