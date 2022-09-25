package com.shahbazly_dev.coingeckotest.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Coin(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    @SerialName("current_price") val price: Double,
    @SerialName("price_change_percentage_24h") val changePercentage: Double
) : Parcelable