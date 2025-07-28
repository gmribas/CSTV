package com.gmribas.cstv.ui.matchdetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmribas.cstv.domain.GetMatchOpponentsUseCase
import com.gmribas.cstv.domain.UseCaseResult
import com.gmribas.cstv.repository.dto.MatchResponseDTO
import com.gmribas.cstv.ui.matchdetails.model.MatchDetailsScreenState
import com.gmribas.cstv.ui.matchdetails.model.MatchDetailsScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.google.gson.Gson
import javax.inject.Inject

@HiltViewModel
class MatchDetailsScreenViewModel @Inject constructor(
    private val getMatchOpponentsUseCase: GetMatchOpponentsUseCase,
    private val gson: Gson
) : ViewModel() {

    private var _state = MutableStateFlow<MatchDetailsScreenState>(MatchDetailsScreenState.MatchDetailsScreenIdleState)
    internal val state: StateFlow<MatchDetailsScreenState> = _state.asStateFlow()

    internal fun onEvent(event: MatchDetailsScreenEvent) {
        when (event) {
            is MatchDetailsScreenEvent.LoadMatchOpponents -> {
                loadMatchOpponents(event.matchDataJson, event.slug)
            }
        }
    }

    private fun loadMatchOpponents(
        matchDataJson: String,
        slug: String
        ) = viewModelScope.launch {
        try {
            _state.value = MatchDetailsScreenState.MatchDetailsScreenLoadingState

            val match = gson.fromJson(matchDataJson, MatchResponseDTO::class.java)
            val opponentsResult = getMatchOpponentsUseCase(slug)

            when (opponentsResult) {
                is UseCaseResult.Success -> {
                    _state.value = MatchDetailsScreenState.MatchDetailsScreenSuccessState(
                        match,
                        opponentsResult.data
                        )
                }

                is UseCaseResult.Error -> {
                    Log.e("MatchDetailsViewModel", "Failed to load match opponents", opponentsResult.error)
                    _state.value = MatchDetailsScreenState.MatchDetailsScreenErrorState(opponentsResult.error.message)
                }
            }
        } catch (e: Exception) {
            Log.e("MatchDetailsViewModel", "Exception in loadMatchOpponents", e)
            _state.value = MatchDetailsScreenState.MatchDetailsScreenErrorState(e.message)
        }
    }
}
