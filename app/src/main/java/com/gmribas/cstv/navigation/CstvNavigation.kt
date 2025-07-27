package com.gmribas.cstv.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.gmribas.cstv.ui.matchdetails.MatchDetailsScreen
import com.gmribas.cstv.ui.matches.MatchesScreen
import com.gmribas.cstv.ui.splash.SplashScreen

@Composable
fun CstvNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(
                onNavigateToMatches = {
                    navController.navigate(Screen.Matches.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(route = Screen.Matches.route) {
            MatchesScreen(
                onItemClick = { match ->
                    navController.navigate(Screen.MatchDetails.createRoute(match.slug))
                }
            )
        }
        
        composable(
            route = Screen.MatchDetails.route,
            arguments = listOf(navArgument("slug") { type = NavType.StringType })
        ) { backStackEntry ->
            val slug = backStackEntry.arguments?.getString("slug") ?: ""
            MatchDetailsScreen(
                slug = slug,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

