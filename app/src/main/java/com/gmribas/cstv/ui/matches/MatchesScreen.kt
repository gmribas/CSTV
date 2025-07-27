package com.gmribas.cstv.ui.matches

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gmribas.cstv.R
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmribas.cstv.repository.dto.MatchResponseDTO
import com.gmribas.cstv.ui.matches.components.MatchItem
import com.gmribas.cstv.ui.matches.model.MatchesScreenState
import com.gmribas.cstv.ui.theme.AccentRed
import com.gmribas.cstv.ui.theme.SPACING_4
import com.gmribas.cstv.ui.theme.SPACING_16
import com.gmribas.cstv.ui.theme.SPACING_20
import com.gmribas.cstv.ui.theme.SPACING_24

@Composable
fun MatchesScreen(
    viewModel: MatchesScreenViewModel = hiltViewModel(),
    onItemClick: (MatchResponseDTO) -> Unit = {},
) {
    val state by viewModel.state.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.loadRecentMatches()
    }
    
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = SPACING_24, vertical = SPACING_20)
            ) {
                Text(
                    text = stringResource(R.string.matches_title),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            
            when (state) {
            is MatchesScreenState.MatchesScreenLoadingState -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = AccentRed
                    )
                }
            }
            
            is MatchesScreenState.MatchesScreenErrorState -> {
                val errorState = state as MatchesScreenState.MatchesScreenErrorState
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.error_prefix) + (errorState.error ?: stringResource(R.string.error_unknown)),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(SPACING_16)
                    )
                }
            }
            
            is MatchesScreenState.MatchesScreenSuccessState -> {
                val successState = state as MatchesScreenState.MatchesScreenSuccessState
                val matches = successState.matches
                if (matches.isNullOrEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.no_matches_found),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(SPACING_16)
                        )
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(SPACING_4)
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
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.welcome_message),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(SPACING_16)
                    )
                }
            }
            }
        }
    }
}
