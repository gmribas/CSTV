package com.gmribas.cstv.repository.mapper

import android.content.Context
import com.gmribas.cstv.core.IMapper
import com.gmribas.cstv.core.extensions.toFormattedDateLabel
import com.gmribas.cstv.data.model.MatchResponse
import com.gmribas.cstv.data.model.Team
import com.gmribas.cstv.repository.dto.MatchResponseDTO
import com.gmribas.cstv.repository.dto.TeamDTO

class MatchMapper(
    private val context: Context,
    private val teamMapper: IMapper<Team, TeamDTO>
) : IMapper<MatchResponse, MatchResponseDTO> {

    override fun toDTO(model: MatchResponse): MatchResponseDTO {
        val teamA = model.opponents?.getOrNull(0)?.opponent?.let { teamMapper.toDTO(it) }
        val teamB = model.opponents?.getOrNull(1)?.opponent?.let { teamMapper.toDTO(it) }

        val isLive = model.status == "running" || model.status == "live"

        val leagueImageUrl = when (val imageUrl = model.league?.imageUrl) {
            is String -> imageUrl
            else -> null
        }

        val formattedDateLabel = model.beginAt?.toFormattedDateLabel(context).orEmpty()

        return MatchResponseDTO(
            slug = model.slug,
            league = model.league?.name,
            serie = model.serie?.name,
            teamA = teamA,
            teamB = teamB,
            status = model.status,
            beginAt = model.beginAt,
            leagueImageUrl = leagueImageUrl,
            isLive = isLive,
            formattedDateLabel = formattedDateLabel
        )
    }
}
