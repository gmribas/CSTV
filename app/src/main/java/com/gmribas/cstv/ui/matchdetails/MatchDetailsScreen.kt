package com.gmribas.cstv.ui.matchdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.gmribas.cstv.R
import com.gmribas.cstv.repository.dto.MatchDetailsResponseDTO
import com.gmribas.cstv.repository.dto.PlayerDetailsDTO
import com.gmribas.cstv.repository.dto.TeamDetailsDTO
import com.gmribas.cstv.ui.matchdetails.model.MatchDetailsScreenState
import com.gmribas.cstv.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchDetailsScreen(
    slug: String,
    onBackClick: () -> Unit = {},
    viewModel: MatchDetailsScreenViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    
    LaunchedEffect(slug) {
        viewModel.loadMatchDetails(slug)
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.match_details_title),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
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
                LoadingContent()
            }
            
            is MatchDetailsScreenState.MatchDetailsScreenErrorState -> {
                val errorState = state as MatchDetailsScreenState.MatchDetailsScreenErrorState
                ErrorContent(error = errorState.error)
            }
            
            is MatchDetailsScreenState.MatchDetailsScreenSuccessState -> {
                val successState = state as MatchDetailsScreenState.MatchDetailsScreenSuccessState
                MatchDetailsContent(matchDetails = successState.matchDetails)
            }
            
            is MatchDetailsScreenState.MatchDetailsScreenIdleState -> {
                // Initial state, loading will be triggered by LaunchedEffect
            }
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = AccentRed
        )
    }
}

@Composable
private fun ErrorContent(error: String?) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.error_prefix) + (error ?: stringResource(R.string.error_unknown)),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(SPACING_16)
        )
    }
}

@Composable
private fun MatchDetailsContent(matchDetails: MatchDetailsResponseDTO) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(SPACING_16),
        verticalArrangement = Arrangement.spacedBy(SPACING_16)
    ) {
        item {
            MatchHeaderCard(matchDetails)
        }
        
        item {
            TeamsSection(
                teamA = matchDetails.teamA,
                teamB = matchDetails.teamB
            )
        }
    }
}

@Composable
private fun MatchHeaderCard(matchDetails: MatchDetailsResponseDTO) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(SPACING_16),
            verticalArrangement = Arrangement.spacedBy(SPACING_8)
        ) {
            // League and Series info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = matchDetails.league.imageUrl,
                    contentDescription = matchDetails.league.name,
                    modifier = Modifier.size(SIZE_32),
                    contentScale = ContentScale.Fit
                )
                
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = matchDetails.league.name,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = matchDetails.serie.fullName,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            // Match time and status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = matchDetails.formattedDateLabel,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                if (matchDetails.isLive) {
                    Text(
                        text = stringResource(R.string.live),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun TeamsSection(
    teamA: TeamDetailsDTO?,
    teamB: TeamDetailsDTO?
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(SPACING_16)
    ) {
        teamA?.let { team ->
            TeamCard(
                team = team,
                isFirst = true
            )
        }
        
        teamB?.let { team ->
            TeamCard(
                team = team,
                isFirst = false
            )
        }
    }
}

@Composable
private fun TeamCard(
    team: TeamDetailsDTO,
    isFirst: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(SPACING_16)
        ) {
            // Team header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(SPACING_12)
                ) {
                    AsyncImage(
                        model = team.imageUrl,
                        contentDescription = team.name,
                        modifier = Modifier.size(SIZE_32),
                        contentScale = ContentScale.Fit
                    )
                    
                    Column {
                        Text(
                            text = team.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        )
                        team.acronym?.let { acronym ->
                            Text(
                                text = acronym,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            
            // Players section
            if (team.players.isNotEmpty()) {
                Spacer(modifier = Modifier.height(SPACING_16))
                
                Text(
                    text = stringResource(R.string.players),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium
                )
                
                Spacer(modifier = Modifier.height(SPACING_8))
                
                team.players.chunked(2).forEach { playerPair ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(SPACING_8)
                    ) {
                        playerPair.forEach { player ->
                            PlayerItem(
                                player = player,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        
                        // Fill remaining space if odd number of players
                        if (playerPair.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(SPACING_8))
                }
            }
        }
    }
}

@Composable
private fun PlayerItem(
    player: PlayerDetailsDTO,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(SIZE_8))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(SPACING_8),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(SPACING_8)
    ) {
        AsyncImage(
            model = player.imageUrl,
            contentDescription = player.name,
            modifier = Modifier
                .size(SIZE_24)
                .clip(RoundedCornerShape(SIZE_12)),
            contentScale = ContentScale.Crop
        )
        
        Text(
            text = player.name,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1
        )
    }
}
