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
import com.gmribas.cstv.ui.matchdetails.MatchDetailsScreenViewModel
import com.gmribas.cstv.ui.matches.MatchesListScreen
import com.gmribas.cstv.ui.matches.MatchesListScreenViewModel
import com.gmribas.cstv.ui.splash.SplashScreen
import com.gmribas.cstv.repository.dto.MatchResponseDTO
import com.google.gson.Gson

@Composable
fun CstvNavigation(
    navController: NavHostController = rememberNavController(),
    onFinish: () -> Unit = {},
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(Screen.Matches.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(route = Screen.Matches.route) {
            val viewModel: MatchesListScreenViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            val gson = Gson()
            
            MatchesListScreen(
                state = state,
                onEvent = viewModel::onEvent,
                onItemClick = { match ->
                    val matchJson = gson.toJson(match)
                    navController.currentBackStackEntry?.savedStateHandle?.set("matchData", matchJson)
                    navController.navigate(Screen.MatchDetails.createRoute(match.slug.orEmpty()))
                },
                onFinish = onFinish
            )
        }
        
        composable(
            route = Screen.MatchDetails.route,
            arguments = listOf(navArgument("slug") { type = NavType.StringType })
        ) { backStackEntry ->
            val viewModel: MatchDetailsScreenViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            val slug = backStackEntry.arguments?.getString("slug").orEmpty()
            val matchDataJson = navController.previousBackStackEntry?.savedStateHandle?.get<String>("matchData")
            MatchDetailsScreen(
                slug = slug,
                matchDataJson = matchDataJson,
                state = state,
                onEvent = viewModel::onEvent,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
