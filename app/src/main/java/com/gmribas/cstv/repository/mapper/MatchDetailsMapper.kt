package com.gmribas.cstv.repository.mapper

import android.content.Context
import com.gmribas.cstv.core.IMapper
import com.gmribas.cstv.core.extensions.toFormattedDateLabel
import com.gmribas.cstv.data.model.MatchDetailsResponse
import com.gmribas.cstv.repository.dto.LeagueDetailsDTO
import com.gmribas.cstv.repository.dto.MatchDetailsResponseDTO
import com.gmribas.cstv.repository.dto.PlayerDetailsDTO
import com.gmribas.cstv.repository.dto.SerieDetailsDTO
import com.gmribas.cstv.repository.dto.TeamDetailsDTO
import com.gmribas.cstv.repository.dto.TournamentDetailsDTO

class MatchDetailsMapper(
    private val context: Context
) : IMapper<MatchDetailsResponse, MatchDetailsResponseDTO> {

    override fun toDTO(model: MatchDetailsResponse): MatchDetailsResponseDTO {
        return MatchDetailsResponseDTO(
            slug = model.match.slug,
            name = model.match.name,
            status = model.match.status,
            beginAt = model.match.beginAt,
            endAt = model.match.endAt,
            isLive = model.match.status == "running" || model.match.status == "live",
            formattedDateLabel = model.match.beginAt.toFormattedDateLabel(context),
            league = LeagueDetailsDTO(
                id = model.match.league.id,
                name = model.match.league.name,
                imageUrl = model.match.league.imageUrl as? String,
                slug = model.match.league.slug
            ),
            serie = SerieDetailsDTO(
                id = model.match.serie.id,
                name = model.match.serie.name,
                fullName = model.match.serie.fullName,
                year = model.match.serie.year,
                season = model.match.serie.season
            ),
            tournament = TournamentDetailsDTO(
                id = model.match.tournament.id,
                name = model.match.tournament.name,
                slug = model.match.tournament.slug
            ),
            teamA = model.teamA?.let { team ->
                TeamDetailsDTO(
                    id = team.id,
                    name = team.name,
                    slug = team.slug,
                    acronym = team.acronym,
                    imageUrl = team.imageUrl,
                    players = team.players?.map { player ->
                        PlayerDetailsDTO(
                            name = player.name ?: "",
                            firstName = player.firstName,
                            lastName = player.lastName,
                            imageUrl = player.imageUrl,
                            nationality = null // TeamPlayer doesn't have nationality field
                        )
                    } ?: emptyList()
                )
            },
            teamB = model.teamB?.let { team ->
                TeamDetailsDTO(
                    id = team.id,
                    name = team.name,
                    slug = team.slug,
                    acronym = team.acronym,
                    imageUrl = team.imageUrl,
                    players = team.players?.map { player ->
                        PlayerDetailsDTO(
                            name = player.name ?: "",
                            firstName = player.firstName,
                            lastName = player.lastName,
                            imageUrl = player.imageUrl,
                            nationality = null // TeamPlayer doesn't have nationality field
                        )
                    } ?: emptyList()
                )
            },
            numberOfGames = model.match.numberOfGames,
            matchType = model.match.matchType
        )
    }
}
