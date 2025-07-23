package com.gmribas.cstv.navigation

sealed class Screen(val route: String) {
    object Matches : Screen("matches")
}
