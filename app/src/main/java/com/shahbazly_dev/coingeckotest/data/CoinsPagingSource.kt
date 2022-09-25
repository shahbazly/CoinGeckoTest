package com.shahbazly_dev.coingeckotest.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shahbazly_dev.coingeckotest.domain.Coin
import com.shahbazly_dev.coingeckotest.base.util.Constants.PAGE_SIZE
import com.shahbazly_dev.coingeckotest.base.util.Constants.STARTING_PAGE_INDEX

class CoinsPagingSource(
    private val currency: String,
    private val service: CoinGeckoService
) : PagingSource<Int, Coin>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Coin> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        return try {
            val coins = service.listCoinsMarkets(
                targetCurrency = currency,
                page = pageIndex,
                resultsPerPage = params.loadSize
            )

            LoadResult.Page(
                data = coins,
                prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex - 1,
                nextKey = if (coins.size == params.loadSize) pageIndex + (params.loadSize / PAGE_SIZE) else null
            )
        } catch (t: Throwable) {
            LoadResult.Error(t)
        }
    }

    /**
     * The refresh key is used for subsequent calls to PagingSource.Load after the initial load.
     */
    override fun getRefreshKey(state: PagingState<Int, Coin>): Int? {
        // get the most recently accessed index in the coins list:
        val anchorPosition = state.anchorPosition ?: return null
        // convert item index to page index:
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        // page doesn't have 'currentKey' property, so need to calculate it manually:
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }
}