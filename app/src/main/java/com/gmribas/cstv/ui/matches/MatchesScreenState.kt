package com.gmribas.cstv.ui.matches

import com.gmribas.cstv.repository.dto.MatchResponseDTO

internal data class MatchesScreenState(
    val matches: List<MatchResponseDTO>? = null
)
