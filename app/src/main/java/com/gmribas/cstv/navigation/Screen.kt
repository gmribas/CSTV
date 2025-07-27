package com.gmribas.cstv.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Matches : Screen("matches")
    object MatchDetails : Screen("match_details/{slug}") {
        fun createRoute(slug: String) = "match_details/$slug"
    }
}
