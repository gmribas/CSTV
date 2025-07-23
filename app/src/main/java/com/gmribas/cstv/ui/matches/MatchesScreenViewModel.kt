package com.gmribas.cstv.ui.matches

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmribas.cstv.domain.GetMatchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchesScreenViewModel @Inject constructor(
    private val getMatchesUseCase: GetMatchesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MatchesScreenState())
    internal val state: StateFlow<MatchesScreenState> = _state

    fun loadRecentMatches() = viewModelScope.launch {
        val today = "" // todo
        getMatchesUseCase(beginAt = today, page = 0)
    }
}
