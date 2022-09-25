package com.shahbazly_dev.coingeckotest.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.shahbazly_dev.coingeckotest.R
import com.shahbazly_dev.coingeckotest.databinding.DetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.details_fragment) {

    private val viewBinding: DetailsFragmentBinding by viewBinding()
    private val viewModel: DetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.message.text = viewModel.id.toString()
    }

    companion object {
        const val TAG = "DETAILS_FRAGMENT"
        const val COIN_ID_KEY = "KEY_COIN_ID"

        fun newInstance(id: String) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putString(COIN_ID_KEY, id)
            }
        }
    }
}