package com.shahbazly_dev.coingeckotest.ui.domain

import kotlinx.serialization.Serializable

@Serializable
data class CoinDetails(
    val name: String,
    val description: Descriptions,
    val categories: List<String>,
    val image: Images
)

@Serializable
data class Descriptions(
    val en: String,
    val ru: String
)

@Serializable
data class Images(
    val thumb: String,
    val small: String,
    val large: String
)