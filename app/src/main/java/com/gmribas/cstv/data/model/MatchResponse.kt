package com.gmribas.cstv.data.model

import com.google.gson.annotations.SerializedName

data class MatchResponse(
    val slug: String,
    val games: List<Game>,
    @SerializedName("game_advantage")
    val gameAdvantage: Any?,
    @SerializedName("begin_at")
    val beginAt: String,
    val opponents: List<Opponent>,
    val draw: Boolean,
    @SerializedName("winner_type")
    val winnerType: String,
    val league: League,
    @SerializedName("videogame_title")
    val videogameTitle: VideogameTitle,
    @SerializedName("detailed_stats")
    val detailedStats: Boolean,
    @SerializedName("match_type")
    val matchType: String,
    val videogame: Videogame,
    val status: String,
    val rescheduled: Boolean,
    @SerializedName("number_of_games")
    val numberOfGames: Long,
    val winner: Player?,
    val forfeit: Boolean,
    @SerializedName("serie_id")
    val serieId: Long,
    @SerializedName("modified_at")
    val modifiedAt: String,
    @SerializedName("original_scheduled_at")
    val originalScheduledAt: String,
    val results: List<Result>,
    val id: Long,
    val serie: Serie,
    val live: Live,
    val name: String,
    @SerializedName("videogame_version")
    val videogameVersion: Any?,
    @SerializedName("streams_list")
    val streamsList: List<StreamsList>,
    @SerializedName("tournament_id")
    val tournamentId: Long,
    @SerializedName("scheduled_at")
    val scheduledAt: String,
    @SerializedName("winner_id")
    val winnerId: Long?,
    @SerializedName("end_at")
    val endAt: String?,
    @SerializedName("league_id")
    val leagueId: Long,
    val tournament: Tournament,
)