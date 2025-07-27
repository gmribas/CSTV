package com.gmribas.cstv.ui.matchdetails.model

import com.gmribas.cstv.repository.dto.MatchDetailsResponseDTO

sealed class MatchDetailsScreenState {
    object MatchDetailsScreenIdleState : MatchDetailsScreenState()
    object MatchDetailsScreenLoadingState : MatchDetailsScreenState()
    data class MatchDetailsScreenSuccessState(val matchDetails: MatchDetailsResponseDTO) : MatchDetailsScreenState()
    data class MatchDetailsScreenErrorState(val error: String?) : MatchDetailsScreenState()
}
