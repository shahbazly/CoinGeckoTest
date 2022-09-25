package com.shahbazly_dev.coingeckotest.ui.details

import com.shahbazly_dev.coingeckotest.domain.Coin
import com.shahbazly_dev.coingeckotest.domain.CoinDetails

data class DetailsUiState(
    val coin: Coin,
    val coinDetails: CoinDetails?,
    val isFetchingDetails: Boolean = false,
    val hasErrors: Boolean = false,
)