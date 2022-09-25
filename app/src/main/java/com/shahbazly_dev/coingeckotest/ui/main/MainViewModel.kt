package com.shahbazly_dev.coingeckotest.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shahbazly_dev.coingeckotest.base.util.Constants.PAGE_SIZE
import com.shahbazly_dev.coingeckotest.data.CoinGeckoService
import com.shahbazly_dev.coingeckotest.data.CoinsPagingSource
import com.shahbazly_dev.coingeckotest.domain.Coin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainViewModel @Inject constructor(coinGeckoService: CoinGeckoService) : ViewModel() {

    val coins: Flow<PagingData<Coin>>
    val currency = MutableLiveData("USD")

    init {
        coins = currency.asFlow()
            .flatMapLatest {
                Pager(
                    PagingConfig(pageSize = PAGE_SIZE)
                ) { CoinsPagingSource(currency = it, coinGeckoService) }
                    .flow
            }
            .cachedIn(viewModelScope)
    }

    fun setCurrency(value: String) {
        if (this.currency.value == value) return
        this.currency.value = value
    }
}