package com.pixelark.capstoneproject.core.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeleteFromCartRequest(
    @Json(name = "id") val id: Int
)

