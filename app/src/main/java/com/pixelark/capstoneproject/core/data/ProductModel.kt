package com.pixelark.capstoneproject.core.data

import java.io.Serializable

data class ProductModel(
    val id: Int = 0,
    val title: String? = null,
    val price: Double? = null,
    val salePrice: Double? = null,
    val description: String? = null,
    val category: String? = null,
    val imageOne: String? = null,
    val imageTwo: String? = null,
    val imageThree: String? = null,
    val rate: Float = 0.0f,
    val count: Int = 0,
    val saleState: Boolean? = null
) : Serializable