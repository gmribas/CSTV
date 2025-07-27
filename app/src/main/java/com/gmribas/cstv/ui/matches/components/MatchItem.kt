package com.gmribas.cstv.ui.matches.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.gmribas.cstv.R
import com.gmribas.cstv.repository.dto.MatchResponseDTO
import com.gmribas.cstv.ui.theme.AccentRed
import com.gmribas.cstv.ui.theme.CardBackground
import com.gmribas.cstv.ui.theme.SIZE_4
import com.gmribas.cstv.ui.theme.SIZE_8
import com.gmribas.cstv.ui.theme.SIZE_12
import com.gmribas.cstv.ui.theme.SIZE_16
import com.gmribas.cstv.ui.theme.SIZE_24
import com.gmribas.cstv.ui.theme.SPACING_6
import com.gmribas.cstv.ui.theme.SPACING_8
import com.gmribas.cstv.ui.theme.SPACING_12
import com.gmribas.cstv.ui.theme.SPACING_16
import com.gmribas.cstv.ui.theme.TextSecondary
import com.gmribas.cstv.ui.theme.TextTertiary

@Composable
fun MatchItem(
    match: MatchResponseDTO,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SPACING_16, vertical = SPACING_8),
        elevation = CardDefaults.cardElevation(defaultElevation = SIZE_4),
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.onSurface,
            containerColor = CardBackground,
        ),
        shape = RoundedCornerShape(SIZE_12)
    ) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SPACING_16)
                    .background(Color.Transparent)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    if (match.isLive) {
                        Box(
                            modifier = Modifier
                                .background(
                                    AccentRed,
                                    shape = RoundedCornerShape(SIZE_16)
                                )
                                .padding(horizontal = SPACING_12, vertical = SPACING_6)
                        ) {
                            Text(
                                text = stringResource(R.string.live_indicator),
                                color = Color.White,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    } else {
                        Text(
                            text = match.formattedDateLabel,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(SIZE_16))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        CustomImage(match.teamA?.imageUrl, match.teamA?.name)

                        Spacer(modifier = Modifier.height(SIZE_8))

                        Text(
                            text = match.teamA?.name ?: stringResource(R.string.team_placeholder),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            maxLines = 2
                        )
                    }
                    
                    Text(
                        text = stringResource(R.string.versus_text),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = SPACING_16)
                    )
                    
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        CustomImage(match.teamB?.imageUrl, match.teamB?.name)

                        Spacer(modifier = Modifier.height(SIZE_8))

                        Text(
                            text = match.teamB?.name ?: stringResource(R.string.team_placeholder),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            maxLines = 2
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(SIZE_16))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(match.leagueImageUrl)
                            .build(),
                        contentDescription = stringResource(R.string.team_logo_description, match.league ?: ""),
                        modifier = Modifier
                            .size(SIZE_24)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(SIZE_8))
                    Text(
                        text = "${match.league}${stringResource(R.string.league_separator)}${match.serie}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
