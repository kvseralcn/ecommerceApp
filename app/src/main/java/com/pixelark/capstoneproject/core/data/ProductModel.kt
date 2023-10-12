package com.pixelark.capstoneproject.core.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorites")
data class ProductModel(
    @PrimaryKey
    var id: Int = 0,
    var title: String? = null,
    var price: Double? = null,
    var salePrice: Double? = null,
    var description: String? = null,
    var category: String? = null,
    var imageOne: String? = null,
    var imageTwo: String? = null,
    var imageThree: String? = null,
    var rate: Float = 0.0f,
    var count: Int = 0,
    var saleState: Boolean? = null
) : Serializable