package com.shahbazly_dev.coingeckotest.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.shahbazly_dev.coingeckotest.ui.data.CoinGeckoService
import com.shahbazly_dev.coingeckotest.ui.utils.Constants.COINS_LIMIT
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(coinGeckoService: CoinGeckoService) : ViewModel() {
    val coins = liveData {
        emit(coinGeckoService.listCoinsMarkets("USD", COINS_LIMIT))
    }
}