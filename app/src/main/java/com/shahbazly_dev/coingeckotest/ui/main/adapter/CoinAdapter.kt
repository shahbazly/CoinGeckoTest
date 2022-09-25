package com.shahbazly_dev.coingeckotest.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.shahbazly_dev.coingeckotest.domain.Coin

class CoinAdapter(var currency: String, private val onClick: (Coin) -> Unit) : PagingDataAdapter<Coin, CoinViewHolder>(DiffUtil()) {

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Coin>() {
        override fun areItemsTheSame(oldItem: Coin, newItem: Coin) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Coin, newItem: Coin) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LayoutInflater.from(parent.context).inflate(
            CoinViewHolder.LAYOUT, parent, false
        ).let {
            CoinViewHolder(it) { position ->
                getItem(position)?.let { coin ->
                    onClick(coin)
                }
            }
        }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val coin = getItem(position)
        if (coin != null)
            holder.onBind(coin, currency)
        else
            holder.clear()
    }
}