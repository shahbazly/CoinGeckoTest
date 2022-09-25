package com.shahbazly_dev.coingeckotest.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.shahbazly_dev.coingeckotest.R
import by.kirich1409.viewbindingdelegate.viewBinding
import com.shahbazly_dev.coingeckotest.databinding.CoinItemBinding
import com.shahbazly_dev.coingeckotest.ui.domain.Coin

class CoinAdapter : ListAdapter<Coin, CoinAdapter.ViewHolder>(DiffUtil()) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val viewBinding: CoinItemBinding by viewBinding()

        fun onBind(coin: Coin) = with(viewBinding) {
            avatarImageView.load(coin.image) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            coinTextView.text = coin.name
            symbolTextView.text = coin.symbol
            priceTextView.text = "${coin.price}"
            changePercentageTextView.text = "${coin.changePercentage}%"
        }
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Coin>() {
        override fun areItemsTheSame(oldItem: Coin, newItem: Coin) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Coin, newItem: Coin) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.coin_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.onBind(getItem(position))
}