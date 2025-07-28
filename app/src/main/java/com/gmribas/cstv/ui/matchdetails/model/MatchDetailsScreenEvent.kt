package com.gmribas.cstv.ui.matchdetails.model

sealed interface MatchDetailsScreenEvent {
    data class LoadMatchOpponents(
        val matchDataJson: String,
        val slug: String
    ) : MatchDetailsScreenEvent
}
