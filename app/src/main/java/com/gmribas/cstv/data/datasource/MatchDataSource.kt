package com.gmribas.cstv.data.datasource

import com.gmribas.cstv.data.api.PandascoreApi
import com.gmribas.cstv.data.model.MatchResponse

class MatchDataSource(
    private val api: PandascoreApi
) : IMatchDataSource {

    override suspend fun getMatches(beginAt: String): MatchResponse {
        return api.getMatches(beginAt)
    }
}
