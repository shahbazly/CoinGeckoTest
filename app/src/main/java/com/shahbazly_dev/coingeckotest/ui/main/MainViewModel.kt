package com.shahbazly_dev.coingeckotest.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.shahbazly_dev.coingeckotest.data.CoinGeckoService
import com.shahbazly_dev.coingeckotest.data.CoinsPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(coinGeckoService: CoinGeckoService) : ViewModel() {

    val coins = Pager(
        PagingConfig(pageSize = 10)
    ) { CoinsPagingSource(currency = "USD", coinGeckoService) }
        .flow
        .cachedIn(viewModelScope)
}