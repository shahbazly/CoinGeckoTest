package com.shahbazly_dev.coingeckotest.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import com.shahbazly_dev.coingeckotest.R
import com.shahbazly_dev.coingeckotest.databinding.MainFragmentBinding
import com.shahbazly_dev.coingeckotest.ui.main.adapter.CoinAdapter
import com.shahbazly_dev.coingeckotest.base.util.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    private val viewBinding: MainFragmentBinding by viewBinding()
    private val viewModel: MainViewModel by viewModels()
    private val adapter get() = viewBinding.coinsRecycler.adapter as CoinAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewBinding.coinsRecycler) {
            adapter = CoinAdapter(viewModel.currency.value ?: "USD")
        }

        adapter.addLoadStateListener { loadStates ->
            viewBinding.coinsRecycler.isVisible = loadStates.refresh is LoadState.NotLoading
            viewBinding.progressIndicator.isVisible = loadStates.refresh is LoadState.Loading
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.coins.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }

        viewBinding.eurChip.setOnClickListener {
            viewModel.setCurrency("EUR")
            adapter.currency = "EUR"
        }
        viewBinding.usdChip.setOnClickListener {
            viewModel.setCurrency("USD")
            adapter.currency = "USD"
        }
    }

}