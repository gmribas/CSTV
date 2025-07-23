package com.gmribas.cstv.data.datasource.match

import com.gmribas.cstv.data.api.PandascoreApi
import com.gmribas.cstv.data.model.MatchResponse

class MatchDataSource(
    private val api: PandascoreApi
) : IMatchDataSource {

    override suspend fun getOrderedMatches(beginAt: String, page: Int): List<MatchResponse> {
        val runningMatches = api.getRunningMatches(page = 1, perPage = 20)
        val upcomingMatches = api.getUpcomingMatches(page = 1, perPage = 30)
        
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
