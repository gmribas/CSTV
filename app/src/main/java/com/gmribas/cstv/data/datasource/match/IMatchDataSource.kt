package com.gmribas.cstv.data.datasource.match

import com.gmribas.cstv.data.model.MatchResponse
import com.gmribas.cstv.data.model.MatchOpponentsResponse

interface IMatchDataSource {
    suspend fun getOrderedMatches(beginAt: String, page: Int): List<MatchResponse>
    
    suspend fun getMatchOpponents(slug: String): MatchOpponentsResponse
}
