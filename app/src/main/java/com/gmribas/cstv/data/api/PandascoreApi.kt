package com.gmribas.cstv.data.api

import com.gmribas.cstv.data.model.MatchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PandascoreApi {

    @GET("csgo/matches")
    suspend fun getMatches(
        @Query("begin_at") beginAt: String,
        @Query("page") page: Int = 0,
    ): List<MatchResponse>

    @GET("csgo/matches")
    suspend fun getMatch(
        @Path("match_id_or_slug") idOrSlug: String,
    ): MatchResponse
}
