package com.gmribas.cstv.data.api

import com.gmribas.cstv.data.model.MatchResponse
import com.gmribas.cstv.data.model.TeamDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PandascoreApi {

    @GET("teams/{team_id_or_slug}")
    suspend fun getTeamById(
        @Path("team_id_or_slug") teamIdOrSlug: Long,
    ): TeamDetailsResponse

    @GET("csgo/matches/running")
    suspend fun getRunningMatches(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 50
    ): List<MatchResponse>
    
    @GET("csgo/matches/upcoming")
    suspend fun getUpcomingMatches(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 50
    ): List<MatchResponse>

    @GET("csgo/matches/{match_id_or_slug}")
    suspend fun getMatch(
        @Path("match_id_or_slug") idOrSlug: String,
    ): MatchResponse
}
