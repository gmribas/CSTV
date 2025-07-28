package com.gmribas.cstv.data.datasource.match

import com.gmribas.cstv.data.api.PandascoreApi
import com.gmribas.cstv.data.model.MatchResponse

class MatchDataSource(
    private val api: PandascoreApi
) : IMatchDataSource {

    companion object {
        const val PAGE_SIZE = 25
    }

    override suspend fun getOrderedMatches(beginAt: String, page: Int): List<MatchResponse> {
        val runningMatches = api.getRunningMatches(page = page, perPage = PAGE_SIZE)
        val upcomingMatches = api.getUpcomingMatches(page = page, perPage = PAGE_SIZE)
        
        val allMatches = (runningMatches + upcomingMatches)
        
        return allMatches.sortedWith(compareBy<MatchResponse> { match ->
            when (match.status) {
                "running", "live" -> 0
                else -> 1
            }
        }.thenBy { it.beginAt })
    }

    override suspend fun getMatch(slug: String): MatchResponse {
        return api.getMatch(slug)
    }
}
