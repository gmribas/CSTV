package com.gmribas.cstv.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gmribas.cstv.data.datasource.match.IMatchDataSource
import com.gmribas.cstv.data.model.MatchResponse
import com.gmribas.cstv.core.extensions.getTodayAsIsoString

class MatchesPagingSource(
    private val matchDataSource: IMatchDataSource
) : PagingSource<Int, MatchResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MatchResponse> {
        val page = params.key ?: 1
        
        return try {
            val today = getTodayAsIsoString()
            val matches = matchDataSource.getOrderedMatches(beginAt = today, page = page)
            
            LoadResult.Page(
                data = matches,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (matches.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            Log.e("MatchesPagingSource", "Failed to load matches for page: $page", exception)
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MatchResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
