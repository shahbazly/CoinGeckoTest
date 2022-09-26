package com.shahbazly_dev.coingeckotest.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.shahbazly_dev.coingeckotest.R
import com.shahbazly_dev.coingeckotest.base.util.launchAndRepeatWithViewLifecycle
import com.shahbazly_dev.coingeckotest.databinding.MainFragmentBinding
import com.shahbazly_dev.coingeckotest.domain.Coin
import com.shahbazly_dev.coingeckotest.ui.details.DetailsFragment
import com.shahbazly_dev.coingeckotest.ui.main.adapter.CoinAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    private val viewBinding: MainFragmentBinding by viewBinding(CreateMethod.INFLATE)
    private val viewModel: MainViewModel by viewModels()
    private val coinAdapter get() = viewBinding.coinsRecycler.adapter as CoinAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        with(viewBinding.coinsRecycler) {
            adapter = CoinAdapter(
                currency = viewModel.currency.value,
                onClick = ::navigateToCoinDetails
            )
            setHasFixedSize(true)
        }

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launchAndRepeatWithViewLifecycle {
            viewModel.coins.collect(coinAdapter::submitData)
        }

        coinAdapter.addLoadStateListener { loadStates ->
            viewBinding.coinsRecycler.isVisible = loadStates.refresh is LoadState.NotLoading
            viewBinding.progressIndicator.isVisible = loadStates.refresh is LoadState.Loading
            viewBinding.errorView.isVisible = loadStates.refresh is LoadState.Error
        }

        viewBinding.usdChip.setOnClickListener {
            viewModel.setCurrency(usdCurrencyCode)
            coinAdapter.currency = usdCurrencyCode
        }

        viewBinding.eurChip.setOnClickListener {
            viewModel.setCurrency(eurCurrencyCode)
            coinAdapter.currency = eurCurrencyCode
        }

        viewBinding.retryButton.setOnClickListener {
            coinAdapter.retry()
        }
    }

    override fun onDestroyView() {
        // Prevents memory leak.
        viewBinding.coinsRecycler.adapter = null
        super.onDestroyView()
    }

    private fun navigateToCoinDetails(coin: Coin) = parentFragmentManager.commit {
        setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
        replace(
            R.id.fragmentContainer,
            DetailsFragment.newInstance(coin),
            DetailsFragment.TAG
        )
        setReorderingAllowed(true)
        addToBackStack(DetailsFragment.TAG)
    }

    companion object {
        const val usdCurrencyCode = "USD"
        const val eurCurrencyCode = "EUR"
    }

}