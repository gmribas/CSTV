package com.gmribas.cstv.ui.matches.model

import com.gmribas.cstv.repository.dto.MatchResponseDTO

internal sealed interface MatchesScreenState {
    data class MatchesScreenSuccessState(val matches: List<MatchResponseDTO>? = null) :
        MatchesScreenState

    data class MatchesScreenErrorState(val error: String? = null) : MatchesScreenState
    data object MatchesScreenLoadingState : MatchesScreenState
    data object MatchesScreenIdleState : MatchesScreenState
}
