package com.gmribas.cstv.ui.matches

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmribas.cstv.repository.dto.MatchResponseDTO
import com.gmribas.cstv.ui.matches.components.MatchItem
import com.gmribas.cstv.ui.matches.model.MatchesScreenState

@Composable
fun MatchesScreen(
    viewModel: MatchesScreenViewModel = hiltViewModel(),
    onItemClick: (MatchResponseDTO) -> Unit = {},
) {
    val state by viewModel.state.collectAsState()
    
    // Load matches when the screen is first composed
    LaunchedEffect(Unit) {
        viewModel.loadRecentMatches()
    }
    
    Column(modifier = Modifier.fillMaxSize()) {
        when (state) {
            is MatchesScreenState.MatchesScreenLoadingState -> {
                // Show loading state
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Loading matches...",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            
            is MatchesScreenState.MatchesScreenErrorState -> {
                // Show error state
                val errorState = state as MatchesScreenState.MatchesScreenErrorState
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Error: ${errorState.error ?: "Unknown error"}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            
            is MatchesScreenState.MatchesScreenSuccessState -> {
                val successState = state as MatchesScreenState.MatchesScreenSuccessState
                val matches = successState.matches
                if (matches.isNullOrEmpty()) {
                    // Show empty state
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "No matches found",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                } else {
                    // Show matches
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        items(matches) { match ->
                            MatchItem(
                                match = match,
                                onClick = { onItemClick(match) }
                            )
                        }
                    }
                }
            }
            
            is MatchesScreenState.MatchesScreenIdleState -> {
                // Initial state
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Welcome to CSTV",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
