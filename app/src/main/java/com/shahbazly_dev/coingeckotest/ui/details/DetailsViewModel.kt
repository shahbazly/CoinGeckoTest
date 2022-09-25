package com.shahbazly_dev.coingeckotest.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahbazly_dev.coingeckotest.data.CoinGeckoService
import com.shahbazly_dev.coingeckotest.domain.Coin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val coinGeckoService: CoinGeckoService
) : ViewModel() {

    val selectedCoin = requireNotNull(savedStateHandle.get<Coin>(DetailsFragment.COIN_KEY)) {
        "Coin shouldn't be null"
    }

    private val _uiState = MutableStateFlow(
        DetailsUiState(
            coin = selectedCoin,
            coinDetails = null,
            isFetchingDetails = true
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        fetchDetails()
    }

    fun fetchDetails() {
        viewModelScope.launch {
            _uiState.update { it.copy(isFetchingDetails = true, hasErrors = false) }

            try {
                val details = coinGeckoService.coinDetails(selectedCoin.id)
                _uiState.update {
                    it.copy(coinDetails = details)
                }
            } catch (ioe: IOException) {
                _uiState.update {
                    it.copy(isFetchingDetails = false, hasErrors = true)
                }
            }
        }
    }
}