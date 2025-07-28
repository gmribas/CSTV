package com.gmribas.cstv.ui.matches.model

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import com.gmribas.cstv.repository.dto.MatchResponseDTO

sealed interface MatchesScreenState {
    data class MatchesScreenSuccessState(
        val matchesPagingFlow: Flow<PagingData<MatchResponseDTO>>
    ) : MatchesScreenState

    data class MatchesScreenErrorState(val error: String? = null) : MatchesScreenState
    data object MatchesScreenLoadingState : MatchesScreenState
    data object MatchesScreenIdleState : MatchesScreenState
}
