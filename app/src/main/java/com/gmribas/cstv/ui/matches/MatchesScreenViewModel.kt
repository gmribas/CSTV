package com.gmribas.cstv.ui.matches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gmribas.cstv.domain.GetMatchesUseCase
import com.gmribas.cstv.repository.dto.MatchResponseDTO
import com.gmribas.cstv.ui.matches.model.MatchScreenEvent
import com.gmribas.cstv.ui.matches.model.MatchesScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MatchesScreenViewModel @Inject constructor(
    private val getMatchesUseCase: GetMatchesUseCase
) : ViewModel() {

    private val matchesPagingFlow: Flow<PagingData<MatchResponseDTO>> = getMatchesUseCase()
        .cachedIn(viewModelScope)

    private var _state = MutableStateFlow<MatchesScreenState>(
        MatchesScreenState.MatchesScreenSuccessState(matchesPagingFlow)
    )
    internal val state: StateFlow<MatchesScreenState> = _state.asStateFlow()

    internal fun onEvent(event: MatchScreenEvent) {
        // Events can be handled for refresh, etc. if needed
        when (event) {
            is MatchScreenEvent.LoadRecentMatches -> {
                // Already loading via paging flow
            }
            is MatchScreenEvent.LoadMoreMatches -> {
                // Handled automatically by paging
            }
            is MatchScreenEvent.ForceRefresh -> {
                // Could trigger refresh on paging source if needed
            }
        }
    }
}
