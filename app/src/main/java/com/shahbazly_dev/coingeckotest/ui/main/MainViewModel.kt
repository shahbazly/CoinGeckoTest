package com.shahbazly_dev.coingeckotest.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shahbazly_dev.coingeckotest.base.util.Constants.PAGE_SIZE
import com.shahbazly_dev.coingeckotest.data.CoinGeckoService
import com.shahbazly_dev.coingeckotest.data.CoinsPagingSource
import com.shahbazly_dev.coingeckotest.domain.Coin
import com.shahbazly_dev.coingeckotest.ui.main.MainFragment.Companion.usdCurrencyCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainViewModel @Inject constructor(coinGeckoService: CoinGeckoService) : ViewModel() {

    private val _currency = MutableStateFlow(usdCurrencyCode)
    val currency: StateFlow<String> = _currency.asStateFlow()

    val coins: Flow<PagingData<Coin>>

    init {
        coins = currency.flatMapLatest {
            Pager(PagingConfig(pageSize = PAGE_SIZE)) {
                CoinsPagingSource(currency = it, coinGeckoService)
            }.flow
        }.cachedIn(viewModelScope)
    }

    fun setCurrency(value: String) {
        if (this.currency.value == value) return
        _currency.tryEmit(value)
    }
}