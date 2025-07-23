package com.gmribas.cstv.data.datasource.match

import com.gmribas.cstv.data.model.MatchResponse

interface IMatchDataSource {
    suspend fun getOrderedMatches(beginAt: String, page: Int): List<MatchResponse>

    suspend fun getMatch(slug: String): MatchResponse
}
