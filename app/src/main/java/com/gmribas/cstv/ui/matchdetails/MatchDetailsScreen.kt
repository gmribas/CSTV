package com.gmribas.cstv.ui.matchdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.gmribas.cstv.R
import com.gmribas.cstv.repository.dto.MatchDetailsResponseDTO
import com.gmribas.cstv.repository.dto.MatchOpponentsResponseDTO
import com.gmribas.cstv.repository.dto.MatchResponseDTO
import com.gmribas.cstv.repository.dto.PlayerDetailsDTO
import com.gmribas.cstv.repository.dto.TeamDetailsDTO
import com.gmribas.cstv.repository.dto.TeamDTO
import com.gmribas.cstv.repository.dto.LeagueDetailsDTO
import com.gmribas.cstv.repository.dto.SerieDetailsDTO
import com.gmribas.cstv.repository.dto.TournamentDetailsDTO
import com.gmribas.cstv.repository.dto.PlayerDTO
import com.gmribas.cstv.ui.common.ErrorContent
import com.gmribas.cstv.ui.common.LoadingContent
import com.gmribas.cstv.ui.matchdetails.model.MatchDetailsScreenState
import com.gmribas.cstv.ui.matchdetails.components.TeamAPlayerItem
import com.gmribas.cstv.ui.matchdetails.components.TeamBPlayerItem
import com.gmribas.cstv.ui.matches.components.TeamColumn
import com.gmribas.cstv.ui.theme.SPACING_8
import com.gmribas.cstv.ui.theme.SPACING_16
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchDetailsScreen(
    slug: String,
    matchDataJson: String? = null,
    onBackClick: () -> Unit = {},
    viewModel: MatchDetailsScreenViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    
    LaunchedEffect(matchDataJson) {
        if (matchDataJson != null) {
            viewModel.loadMatchOpponents(matchDataJson, slug)
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top App Bar with league + serie name
        var title by remember {
            mutableStateOf("")
        }

        TopAppBar(
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        )

        when (state) {
            is MatchDetailsScreenState.MatchDetailsScreenLoadingState -> {
                LoadingContent(modifier = Modifier.fillMaxSize())
            }
            
            is MatchDetailsScreenState.MatchDetailsScreenErrorState -> {
                val errorState = state as MatchDetailsScreenState.MatchDetailsScreenErrorState
                ErrorContent(
                    error = errorState.error,
                    modifier = Modifier.fillMaxSize()
                )
            }
            
            is MatchDetailsScreenState.MatchDetailsScreenSuccessState -> {
                val successState = state as MatchDetailsScreenState.MatchDetailsScreenSuccessState
                val separator = stringResource(R.string.league_separator)
                title = "${successState.matchDetails.league} $separator ${successState.matchDetails.serie}"
                MatchDetailsContent(
                    matchDetails = successState.matchDetails,
                    opponentsResponse = successState.opponentsResponse
                )
            }
            
            is MatchDetailsScreenState.MatchDetailsScreenIdleState -> {}
        }
    }
}

@Composable
private fun MatchDetailsContent(
    matchDetails: MatchResponseDTO,
    opponentsResponse: MatchOpponentsResponseDTO?
) {
    LazyColumn {
        item {
            MatchHeaderCard(matchDetails)
        }

        if (opponentsResponse != null) {
            val playersA = opponentsResponse.opponents.getOrNull(0)?.players ?: emptyList()
            val playersB = opponentsResponse.opponents.getOrNull(1)?.players ?: emptyList()
            val maxPlayers = maxOf(playersA.size, playersB.size)
            
            items(maxPlayers) { i ->
                Column {
                    Row {
                        TeamAPlayerItem(
                            modifier = Modifier.weight(1f),
                            player = playersA.getOrNull(i)
                        )
                        
                        Spacer(modifier = Modifier.width(SPACING_16))
                        
                        TeamBPlayerItem(
                            modifier = Modifier.weight(1f),
                            player = playersB.getOrNull(i)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(SPACING_16))
                }
            }
        } else {
            item {
                LoadingContent()
            }
        }
    }
}

@Composable
private fun MatchHeaderCard(matchDetails: MatchResponseDTO) {
    
        Column(
            modifier = Modifier
            .fillMaxWidth()
            .padding(SPACING_16),
            verticalArrangement = Arrangement.spacedBy(SPACING_8)
        ) {
            // Teams and Date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TeamColumn(team = matchDetails.teamA)
                Text(
                    text = stringResource(R.string.versus_text),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                TeamColumn(team = matchDetails.teamB)
            }

            Spacer(modifier = Modifier.height(SPACING_8))

            // Match time and status
            Text(
                text = matchDetails.formattedDateLabel,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    
}
