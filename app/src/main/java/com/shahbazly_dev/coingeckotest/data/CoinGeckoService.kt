package com.shahbazly_dev.coingeckotest.data

import com.shahbazly_dev.coingeckotest.domain.Coin
import com.shahbazly_dev.coingeckotest.domain.CoinDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinGeckoService {

    @GET("api/v3/coins/markets")
    suspend fun listCoinsMarkets(
        @Query("vs_currency") targetCurrency: String,
        @Query("page") page: Int,
        @Query("per_page") resultsPerPage: Int
    ): List<Coin>

    @GET("api/v3/coins/{id}")
    suspend fun coinDetails(
        @Path("id") id: String
    ): CoinDetails
}