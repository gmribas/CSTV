package com.gmribas.cstv.data.datasource

import com.gmribas.cstv.data.api.PandascoreApi
import com.gmribas.cstv.data.model.MatchResponse

class MatchDataSource(
    private val api: PandascoreApi
) : IMatchDataSource {

    override suspend fun getMatches(beginAt: String, page: Int): List<MatchResponse> {
        return api.getMatches(beginAt, page)
    }

    override suspend fun getMatch(slug: String): MatchResponse {
        return api.getMatch(slug)
    }
}
