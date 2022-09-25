package com.shahbazly_dev.coingeckotest.ui.details

import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import coil.transform.CircleCropTransformation
import com.shahbazly_dev.coingeckotest.R
import com.shahbazly_dev.coingeckotest.base.util.launchAndRepeatWithViewLifecycle
import com.shahbazly_dev.coingeckotest.databinding.DetailsFragmentBinding
import com.shahbazly_dev.coingeckotest.domain.Coin
import com.shahbazly_dev.coingeckotest.domain.CoinDetails
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.details_fragment) {

    private val viewBinding: DetailsFragmentBinding by viewBinding()
    private val viewModel: DetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launchAndRepeatWithViewLifecycle {
            viewModel.uiState.collectLatest { uiState ->
                if (uiState.coinDetails != null) {
                    setCoinDetails(uiState.coinDetails)
                } else {
                    setLoadState(uiState)
                }
            }
        }

        with(viewBinding) {
            topAppBar.title = viewModel.selectedCoin.name

            topAppBar.setNavigationOnClickListener {
                parentFragmentManager.popBackStack()
            }
            retryButton.setOnClickListener {
                viewModel.fetchDetails()
            }
        }
    }

    private fun setCoinDetails(details: CoinDetails) = with(viewBinding) {
        coinImageView.load(details.image.large) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }
        descriptionTextView.text = SpannableString(details.description.en.let {
            Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT)
        })
        categoriesTextView.text = details.categories.joinToString()

        contentContainer.isVisible = true
        progressIndicator.isVisible = false
        errorView.isVisible = false
    }

    private fun setLoadState(state: DetailsUiState) = with(viewBinding) {
        progressIndicator.isVisible = state.isFetchingDetails
        errorView.isVisible = state.hasErrors

        contentContainer.isVisible = false
    }

    companion object {
        const val TAG = "DETAILS_FRAGMENT"
        const val COIN_KEY = "KEY_COIN"

        fun newInstance(coin: Coin) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(COIN_KEY, coin)
            }
        }
    }
}