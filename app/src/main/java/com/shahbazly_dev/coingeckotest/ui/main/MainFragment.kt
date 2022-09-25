package com.shahbazly_dev.coingeckotest.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.shahbazly_dev.coingeckotest.R
import com.shahbazly_dev.coingeckotest.databinding.MainFragmentBinding
import com.shahbazly_dev.coingeckotest.ui.main.adapter.CoinAdapter
import com.shahbazly_dev.coingeckotest.ui.utils.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    private val viewBinding: MainFragmentBinding by viewBinding()
    private val viewModel: MainViewModel by viewModels()
    private val adapter get() = viewBinding.coinsRecycler.adapter as CoinAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewBinding.coinsRecycler) {
            adapter = CoinAdapter("USD")
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.coins.collect(adapter::submitData)
        }
    }

}