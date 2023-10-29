package com.pixelark.capstoneproject.core.data

import java.io.Serializable

data class CampaignModel(
    val id: String,
    val title: String,
    val imageUrl: String,
    val videoUrl: String,
    val type: Int,
    val text: String
) : Serializable
