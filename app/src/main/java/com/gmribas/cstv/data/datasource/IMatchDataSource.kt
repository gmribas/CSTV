package com.gmribas.cstv.data.datasource

import com.gmribas.cstv.data.model.MatchResponse

interface IMatchDataSource {
    suspend fun getMatches(beginAt: String): MatchResponse
}
