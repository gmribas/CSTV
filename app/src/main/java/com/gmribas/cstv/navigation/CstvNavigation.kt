package com.gmribas.cstv.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.gmribas.cstv.ui.matchdetails.MatchDetailsScreen
import com.gmribas.cstv.ui.matches.MatchesScreen
import com.gmribas.cstv.ui.matches.MatchesScreenViewModel
import com.gmribas.cstv.ui.splash.SplashScreen
import com.google.gson.Gson
import com.gmribas.cstv.repository.dto.MatchResponseDTO

@Composable
fun CstvNavigation(
    navController: NavHostController = rememberNavController(),
    onFinish: () -> Unit = {}
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
            val viewModel: MatchesScreenViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            val gson = Gson()
            
            MatchesScreen(
                state = state,
                onEvent = viewModel::onEvent,
                onItemClick = { match ->
                    val matchJson = gson.toJson(match)
                    navController.currentBackStackEntry?.savedStateHandle?.set("matchData", matchJson)
                    navController.navigate(Screen.MatchDetails.createRoute(match.slug))
                },
                onFinish = onFinish
            )
        }
        
        composable(
            route = Screen.MatchDetails.route,
            arguments = listOf(navArgument("slug") { type = NavType.StringType })
        ) { backStackEntry ->
            val slug = backStackEntry.arguments?.getString("slug") ?: ""
            val matchDataJson = navController.previousBackStackEntry?.savedStateHandle?.get<String>("matchData")
            MatchDetailsScreen(
                slug = slug,
                matchDataJson = matchDataJson,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
