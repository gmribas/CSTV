package com.gmribas.cstv.ui.matches.model

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import com.gmribas.cstv.repository.dto.MatchResponseDTO

sealed interface MatchesListScreenState {
    data class MatchesScreenSuccessState(
        val matchesPagingFlow: Flow<PagingData<MatchResponseDTO>>
    ) : MatchesListScreenState

    data class MatchesScreenErrorState(val error: String? = null) : MatchesListScreenState
    data object MatchesScreenLoadingState : MatchesListScreenState
    data object MatchesScreenIdleState : MatchesListScreenState
}
