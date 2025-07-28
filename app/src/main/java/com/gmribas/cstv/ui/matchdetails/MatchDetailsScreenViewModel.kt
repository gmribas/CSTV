package com.gmribas.cstv.ui.matchdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmribas.cstv.domain.GetMatchDetailsUseCase
import com.gmribas.cstv.domain.UseCaseResult
import com.gmribas.cstv.repository.dto.MatchResponseDTO
import com.gmribas.cstv.ui.matchdetails.model.MatchDetailsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.google.gson.Gson
import javax.inject.Inject

@HiltViewModel
class MatchDetailsScreenViewModel @Inject constructor(
    private val getMatchDetailsUseCase: GetMatchDetailsUseCase
) : ViewModel() {

    private var _state = MutableStateFlow<MatchDetailsScreenState>(MatchDetailsScreenState.MatchDetailsScreenIdleState)
    internal val state: StateFlow<MatchDetailsScreenState> = _state.asStateFlow()

    fun loadMatchDetails(slug: String) = viewModelScope.launch {
        try {
            _state.value = MatchDetailsScreenState.MatchDetailsScreenLoadingState

            val result = getMatchDetailsUseCase(slug)

            when (result) {
                is UseCaseResult.Success -> {
                    _state.value = MatchDetailsScreenState.MatchDetailsScreenSuccessState(result.data)
                }

                is UseCaseResult.Error -> {
                    _state.value = MatchDetailsScreenState.MatchDetailsScreenErrorState(result.error.message)
                }
            }
        } catch (e: Exception) {
            _state.value = MatchDetailsScreenState.MatchDetailsScreenErrorState(e.message)
        }
    }
    
    fun loadFromSerializedData(matchDataJson: String) {
        try {
            val gson = Gson()
            val match = gson.fromJson(matchDataJson, MatchResponseDTO::class.java)
            val matchDetails = mapToMatchDetailsDTO(match)
            _state.value = MatchDetailsScreenState.MatchDetailsScreenSuccessState(matchDetails)
        } catch (e: Exception) {
            _state.value = MatchDetailsScreenState.MatchDetailsScreenErrorState("Failed to parse match data: ${e.message}")
        }
    }
    
    private fun mapToMatchDetailsDTO(match: MatchResponseDTO): com.gmribas.cstv.repository.dto.MatchDetailsResponseDTO {
        return com.gmribas.cstv.repository.dto.MatchDetailsResponseDTO(
            slug = match.slug,
            name = "", // Not available in MatchResponseDTO
            status = match.status,
            beginAt = match.beginAt,
            endAt = null, // Not available in MatchResponseDTO
            isLive = match.isLive,
            formattedDateLabel = match.formattedDateLabel,
            league = com.gmribas.cstv.repository.dto.LeagueDetailsDTO(
                id = 0L, // Not available in MatchResponseDTO
                name = match.league,
                imageUrl = match.leagueImageUrl,
                slug = "" // Not available in MatchResponseDTO
            ),
            serie = com.gmribas.cstv.repository.dto.SerieDetailsDTO(
                id = 0L, // Not available in MatchResponseDTO
                name = match.serie,
                fullName = match.serie, // Using serie as fullName since fullName is not available
                year = 0L, // Not available in MatchResponseDTO
                season = null // Not available in MatchResponseDTO
            ),
            tournament = com.gmribas.cstv.repository.dto.TournamentDetailsDTO(
                id = 0L, // Not available in MatchResponseDTO
                name = "", // Not available in MatchResponseDTO
                slug = "" // Not available in MatchResponseDTO
            ),
            teamA = match.teamA?.let { team ->
                com.gmribas.cstv.repository.dto.TeamDetailsDTO(
                    id = team.id,
                    name = team.name,
                    slug = team.slug,
                    acronym = team.acronym,
                    imageUrl = team.imageUrl,
                    players = team.players?.map { player ->
                        com.gmribas.cstv.repository.dto.PlayerDetailsDTO(
                            name = player.name,
                            firstName = null, // Not available in PlayerDTO
                            lastName = null, // Not available in PlayerDTO
                            imageUrl = player.imageUrl,
                            nationality = null // Not available in PlayerDTO
                        )
                    } ?: emptyList()
                )
            },
            teamB = match.teamB?.let { team ->
                com.gmribas.cstv.repository.dto.TeamDetailsDTO(
                    id = team.id,
                    name = team.name,
                    slug = team.slug,
                    acronym = team.acronym,
                    imageUrl = team.imageUrl,
                    players = team.players?.map { player ->
                        com.gmribas.cstv.repository.dto.PlayerDetailsDTO(
                            name = player.name,
                            firstName = null, // Not available in PlayerDTO
                            lastName = null, // Not available in PlayerDTO
                            imageUrl = player.imageUrl,
                            nationality = null // Not available in PlayerDTO
                        )
                    } ?: emptyList()
                )
            },
            numberOfGames = 0L, // Not available in MatchResponseDTO
            matchType = "" // Not available in MatchResponseDTO
        )
    }
}
