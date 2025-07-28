package com.gmribas.cstv.ui.matches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gmribas.cstv.domain.GetMatchesUseCase
import com.gmribas.cstv.repository.dto.MatchResponseDTO
import com.gmribas.cstv.ui.matches.model.MatchScreenEvent
import com.gmribas.cstv.ui.matches.model.MatchesListScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MatchesListScreenViewModel @Inject constructor(
    private val getMatchesUseCase: GetMatchesUseCase
) : ViewModel() {

    private val matchesPagingFlow: Flow<PagingData<MatchResponseDTO>> = getMatchesUseCase()
        .cachedIn(viewModelScope)

    private var _state = MutableStateFlow<MatchesListScreenState>(
        MatchesListScreenState.MatchesScreenSuccessState(matchesPagingFlow)
    )
    internal val state: StateFlow<MatchesListScreenState> = _state.asStateFlow()

    internal fun onEvent(event: MatchScreenEvent) {
        when (event) {
            is MatchScreenEvent.LoadRecentMatches -> {
            }
            is MatchScreenEvent.LoadMoreMatches -> {
            }
            is MatchScreenEvent.ForceRefresh -> {
            }
        }
    }
}
