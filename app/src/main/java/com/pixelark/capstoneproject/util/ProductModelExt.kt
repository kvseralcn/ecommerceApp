package com.pixelark.capstoneproject.util

import com.pixelark.capstoneproject.core.data.ProductModel

fun ProductModel.getPriceWithCurrency(currency: String): String {
    return "$price $currency"
}

fun ProductModel.getSalePriceWithCurrency(currency: String): String {
    return "$salePrice $currency"
}