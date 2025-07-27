package com.gmribas.cstv.ui.matchdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmribas.cstv.domain.GetMatchDetailsUseCase
import com.gmribas.cstv.domain.UseCaseResult
import com.gmribas.cstv.ui.matchdetails.model.MatchDetailsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
}
