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
import com.gmribas.cstv.repository.dto.MatchResponseDTO
import com.gmribas.cstv.repository.dto.PlayerDetailsDTO
import com.gmribas.cstv.repository.dto.TeamDetailsDTO
import com.gmribas.cstv.repository.dto.LeagueDetailsDTO
import com.gmribas.cstv.repository.dto.SerieDetailsDTO
import com.gmribas.cstv.repository.dto.TournamentDetailsDTO
import com.gmribas.cstv.repository.dto.PlayerDTO
import com.gmribas.cstv.ui.common.ErrorContent
import com.gmribas.cstv.ui.common.LoadingContent
import com.gmribas.cstv.ui.matchdetails.model.MatchDetailsScreenState
import com.gmribas.cstv.ui.matches.components.TeamColumn
import com.gmribas.cstv.ui.theme.*
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
            viewModel.loadFromSerializedData(matchDataJson)
        } else {
            viewModel.loadMatchDetails(slug)
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
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
                // Top App Bar with league + serie name
                TopAppBar(
                    title = {
                        Text(
                            text = "${successState.matchDetails.league.name} + ${successState.matchDetails.serie.name}",
                            style = MaterialTheme.typography.bodyLarge,
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
                
                NewMatchDetailsContent(matchDetails = successState.matchDetails)
            }
            
            is MatchDetailsScreenState.MatchDetailsScreenIdleState -> {
                // Initial state, loading will be triggered by LaunchedEffect
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
            }
        }
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

@Composable
private fun NewMatchDetailsContent(matchDetails: MatchDetailsResponseDTO) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(SPACING_24),
        verticalArrangement = Arrangement.spacedBy(SPACING_24)
    ) {
        // Team A vs Team B header (similar to match item)
        item {
            TeamVersusHeader(
                teamA = matchDetails.teamA,
                teamB = matchDetails.teamB,
                isLive = matchDetails.isLive
            )
        }
        
        // Match date (same as match item)
        item {
            Text(
                text = matchDetails.formattedDateLabel,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        
        // Players section
        item {
            PlayersSection(
                teamA = matchDetails.teamA,
                teamB = matchDetails.teamB
            )
        }
    }
}

@Composable
private fun TeamVersusHeader(
    teamA: TeamDetailsDTO?,
    teamB: TeamDetailsDTO?,
    isLive: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(SPACING_16),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
            // Team A
            TeamColumn(
                team = teamA?.let { 
                    com.gmribas.cstv.repository.dto.TeamDTO(
                        id = it.id,
                        name = it.name,
                        slug = it.slug,
                        acronym = it.acronym,
                        imageUrl = it.imageUrl
                    )
                },
                modifier = Modifier.weight(1f)
            )
            
            // VS text
            Text(
                text = stringResource(R.string.versus_text),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = SPACING_8)
            )
            
            // Team B
            TeamColumn(
                team = teamB?.let { 
                    com.gmribas.cstv.repository.dto.TeamDTO(
                        id = it.id,
                        name = it.name,
                        slug = it.slug,
                        acronym = it.acronym,
                        imageUrl = it.imageUrl
                    )
                },
                modifier = Modifier.weight(1f)
            )
    }
}

@Composable
private fun PlayersSection(
    teamA: TeamDetailsDTO?,
    teamB: TeamDetailsDTO?
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(SPACING_16)
    ) {
        // Team A players (left side)
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(SPACING_12)
        ) {
            teamA?.players?.forEach { player ->
                NewPlayerItem(
                    player = player,
                    isTeamA = true
                )
            }
        }
        
        // Team B players (right side)
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(SPACING_12)
        ) {
            teamB?.players?.forEach { player ->
                NewPlayerItem(
                    player = player,
                    isTeamA = false
                )
            }
        }
    }
}

@Composable
private fun NewPlayerItem(
    player: PlayerDetailsDTO,
    isTeamA: Boolean
) {
    Box {
        // Background with rounded corners (same as match item)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(SIZE_12))
                .background(CardBackground)
                .padding(SPACING_16)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Nickname (center)
                Text(
                    text = player.name, // Using name as nickname since we don't have separate nickname
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(SPACING_4))
                
                // Player name (center horizontal, below nickname)
                Text(
                    text = player.firstName?.let { first ->
                        player.lastName?.let { last -> "$first $last" } ?: first
                    } ?: player.name, // Fallback to name if first/last not available
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
        
        // Player image (positioned 10.dp above container)
        AsyncImage(
            model = player.imageUrl,
            contentDescription = player.name,
            modifier = Modifier
                .size(SIZE_48)
                .offset(y = (-10).dp)
                .clip(RoundedCornerShape(SIZE_8))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .align(
                    if (isTeamA) Alignment.TopEnd else Alignment.TopStart
                ),
            contentScale = ContentScale.Crop
        )
    }
}

// Mapping function to convert MatchResponseDTO to MatchDetailsResponseDTO
// This allows us to reuse the existing UI components while working with the serialized data
private fun mapToMatchDetailsDTO(match: MatchResponseDTO): MatchDetailsResponseDTO {
    return MatchDetailsResponseDTO(
        slug = match.slug,
        name = "", // Not available in MatchResponseDTO, commenting with placeholder
        status = match.status,
        beginAt = match.beginAt,
        endAt = null, // Not available in MatchResponseDTO
        isLive = match.isLive,
        formattedDateLabel = match.formattedDateLabel,
        league = LeagueDetailsDTO(
            id = 0L, // Not available in MatchResponseDTO
            name = match.league,
            imageUrl = match.leagueImageUrl,
            slug = "" // Not available in MatchResponseDTO
        ),
        serie = SerieDetailsDTO(
            id = 0L, // Not available in MatchResponseDTO
            name = match.serie,
            fullName = match.serie, // Using serie as fullName since fullName is not available
            year = 0L, // Not available in MatchResponseDTO
            season = null // Not available in MatchResponseDTO
        ),
        tournament = TournamentDetailsDTO(
            id = 0L, // Not available in MatchResponseDTO
            name = "", // Not available in MatchResponseDTO
            slug = "" // Not available in MatchResponseDTO
        ),
        teamA = match.teamA?.let { team ->
            TeamDetailsDTO(
                id = team.id,
                name = team.name,
                slug = team.slug,
                acronym = team.acronym,
                imageUrl = team.imageUrl,
                players = team.players?.map { player ->
                    PlayerDetailsDTO(
                        name = player.name,
                        firstName = null, // Not available in PlayerDTO
                        lastName = null, // Not available in PlayerDTO
                        imageUrl = player.imageUrl,
                        nationality = null // Not available in PlayerDTO
                    )
                } ?: emptyList()
            )
        },
        teamB = match.teamB?.let { team ->
            TeamDetailsDTO(
                id = team.id,
                name = team.name,
                slug = team.slug,
                acronym = team.acronym,
                imageUrl = team.imageUrl,
                players = team.players?.map { player ->
                    PlayerDetailsDTO(
                        name = player.name,
                        firstName = null, // Not available in PlayerDTO
                        lastName = null, // Not available in PlayerDTO
                        imageUrl = player.imageUrl,
                        nationality = null // Not available in PlayerDTO
                    )
                } ?: emptyList()
            )
        },
        numberOfGames = 0L, // Not available in MatchResponseDTO
        matchType = "" // Not available in MatchResponseDTO
    )
}
