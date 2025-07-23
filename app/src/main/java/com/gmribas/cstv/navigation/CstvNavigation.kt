package com.gmribas.cstv.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gmribas.cstv.ui.matches.MatchesScreen

@Composable
fun CstvNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Matches.route
    ) {
        composable(route = Screen.Matches.route) {
            MatchesScreen(
                onItemClick = { match ->
                    // Handle match item click - navigate to match details
                    // For now, just print the match info
                    println("Clicked on match: ${match.slug}")
                }
            )
        }
    }
}

