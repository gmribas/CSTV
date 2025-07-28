package com.gmribas.cstv.ui.matches.model

sealed interface MatchScreenEvent {
    object LoadRecentMatches : MatchScreenEvent
    object LoadMoreMatches : MatchScreenEvent
    object ForceRefresh : MatchScreenEvent
}