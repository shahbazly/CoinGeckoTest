package com.shahbazly_dev.coingeckotest.ui.data

import com.shahbazly_dev.coingeckotest.ui.domain.Coin
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinGeckoService {

    @GET("api/v3/coins/markets")
    suspend fun listCoinsMarkets(
        @Query("vs_currency") targetCurrency: String
    ): List<Coin>
}