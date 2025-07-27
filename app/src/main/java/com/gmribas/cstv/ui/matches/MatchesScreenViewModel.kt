package com.gmribas.cstv.ui.matches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmribas.cstv.domain.GetMatchesUseCase
import com.gmribas.cstv.domain.UseCaseResult
import com.gmribas.cstv.ui.matches.model.MatchesScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.gmribas.cstv.core.extensions.getTodayAsIsoString
import javax.inject.Inject

@HiltViewModel
class MatchesScreenViewModel @Inject constructor(
    private val getMatchesUseCase: GetMatchesUseCase
) : ViewModel() {

    private var _state = MutableStateFlow<MatchesScreenState>(MatchesScreenState.MatchesScreenIdleState)
    internal val state: StateFlow<MatchesScreenState> = _state.asStateFlow()

    fun loadRecentMatches() = viewModelScope.launch {
        try {
            _state.value = MatchesScreenState.MatchesScreenLoadingState

            val today = getTodayAsIsoString()

            val result = getMatchesUseCase(beginAt = today, page = 1)

            when (result) {
                is UseCaseResult.Success -> {
                    _state.value = MatchesScreenState.MatchesScreenSuccessState(result.data)
                }

                is UseCaseResult.Error -> {
                    _state.value = MatchesScreenState.MatchesScreenErrorState(result.error.message)
                }
            }
        } catch (e: Exception) {
            _state.value = MatchesScreenState.MatchesScreenErrorState(e.message)
        }
    }
}
