package com.shahbazly_dev.coingeckotest.ui.main.adapter

import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.clear
import coil.load
import coil.transform.CircleCropTransformation
import com.shahbazly_dev.coingeckotest.R
import com.shahbazly_dev.coingeckotest.databinding.CoinItemBinding
import com.shahbazly_dev.coingeckotest.domain.Coin

class CoinViewHolder(view: View, clickAtPosition: (Int) -> Unit) : RecyclerView.ViewHolder(view) {
    private val viewBinding: CoinItemBinding by viewBinding()

    init {
        viewBinding.coinContainer.setOnClickListener {
            val adapterPos = bindingAdapterPosition
            if (adapterPos != RecyclerView.NO_POSITION) clickAtPosition(adapterPos)
        }
    }

    fun onBind(coin: Coin, currency: String) = with(viewBinding) {
        coinImageView.load(coin.image) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }
        coinTextView.text = coin.name
        symbolTextView.text = coin.symbol.uppercase()

        coin.price.let { coinPrice ->
            priceTextView.text = when(currency) {
                "USD" -> root.context.getString(R.string.dollarCurrencyPrice, coinPrice)
                "EUR" -> root.context.getString(R.string.euroCurrencyPrice, coinPrice)
                else -> root.context.getString(R.string.defaultCurrencyPrice, coinPrice)
            }
        }
        coin.changePercentage.let { percentage ->
            when {
                percentage > 0 -> {
                    changePercentageTextView.setTextColor(
                        ContextCompat.getColor(
                            root.context,
                            R.color.green
                        )
                    )
                    changePercentageTextView.text =
                        root.context.getString(R.string.positiveChangePercentage, percentage)
                }
                percentage == 0.0 -> {
                    changePercentageTextView.setTextColor(
                        ContextCompat.getColor(
                            root.context,
                            R.color.gray_500
                        )
                    )
                    changePercentageTextView.text =
                        root.context.getString(R.string.negativeChangePercentage, percentage)
                }
                percentage < 0 -> {
                    changePercentageTextView.setTextColor(
                        ContextCompat.getColor(
                            root.context,
                            R.color.red
                        )
                    )
                    changePercentageTextView.text =
                        root.context.getString(R.string.negativeChangePercentage, percentage)
                }
            }
        }
    }

    fun clear() = with(viewBinding) {
        coinImageView.clear()
        coinTextView.text = ""
        symbolTextView.text = ""
        priceTextView.text = ""
        changePercentageTextView.text = ""
    }

    companion object {
        @LayoutRes
        val LAYOUT = R.layout.coin_item
    }
}