package com.shahbazly_dev.coingeckotest.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import com.shahbazly_dev.coingeckotest.R
import com.shahbazly_dev.coingeckotest.base.util.launchAndRepeatWithViewLifecycle
import com.shahbazly_dev.coingeckotest.databinding.MainFragmentBinding
import com.shahbazly_dev.coingeckotest.domain.Coin
import com.shahbazly_dev.coingeckotest.ui.details.DetailsFragment
import com.shahbazly_dev.coingeckotest.ui.main.adapter.CoinAdapter
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
            adapter = CoinAdapter(viewModel.currency.value) { coin ->
                navigateToCoinDetails(coin)
            }
        }

        adapter.addLoadStateListener { loadStates ->
            viewBinding.coinsRecycler.isVisible = loadStates.refresh is LoadState.NotLoading
            viewBinding.progressIndicator.isVisible = loadStates.refresh is LoadState.Loading
            viewBinding.errorView.isVisible = loadStates.refresh is LoadState.Error
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.coins.collectLatest(adapter::submitData)
        }

        viewBinding.eurChip.setOnClickListener {
            viewModel.setCurrency(eurCurrencyCode)
            adapter.currency = eurCurrencyCode
        }

        viewBinding.usdChip.setOnClickListener {
            viewModel.setCurrency(usdCurrencyCode)
            adapter.currency = usdCurrencyCode
        }

        viewBinding.retryButton.setOnClickListener {
            adapter.retry()
        }
    }

    private fun navigateToCoinDetails(coin: Coin) = parentFragmentManager.commit {
        replace(R.id.container, DetailsFragment.newInstance(coin), DetailsFragment.TAG)
        setReorderingAllowed(true)
        addToBackStack(DetailsFragment.TAG)
    }

    companion object {
        const val usdCurrencyCode = "USD"
        const val eurCurrencyCode = "EUR"
    }

}