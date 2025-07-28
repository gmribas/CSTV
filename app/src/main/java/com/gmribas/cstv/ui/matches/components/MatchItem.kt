package com.gmribas.cstv.ui.matches.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.style.TextOverflow
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.gmribas.cstv.R
import com.gmribas.cstv.repository.dto.MatchResponseDTO
import com.gmribas.cstv.ui.common.TeamColumn
import com.gmribas.cstv.ui.theme.AccentRed
import com.gmribas.cstv.ui.theme.CardBackground
import com.gmribas.cstv.ui.theme.SIZE_0
import com.gmribas.cstv.ui.theme.SIZE_4
import com.gmribas.cstv.ui.theme.SIZE_8
import com.gmribas.cstv.ui.theme.SIZE_12
import com.gmribas.cstv.ui.theme.SIZE_16
import com.gmribas.cstv.ui.theme.SIZE_190
import com.gmribas.cstv.ui.theme.SPACING_4
import com.gmribas.cstv.ui.theme.SPACING_8
import com.gmribas.cstv.ui.theme.SPACING_16
import com.gmribas.cstv.ui.theme.TextSecondary

@Composable
fun MatchItem(
    match: MatchResponseDTO,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(SIZE_190),
        elevation = CardDefaults.cardElevation(defaultElevation = SIZE_4),
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.onSurface,
            containerColor = CardBackground,
        ),
        shape = RoundedCornerShape(SIZE_12)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            DateTag(
                modifier = Modifier.align(Alignment.TopEnd),
                match = match
            )

            Row(
                modifier = Modifier
                    .align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TeamColumn(
                    team = match.teamA,
                )

                Text(
                    text = stringResource(R.string.versus_text),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = SPACING_16)
                )

                TeamColumn(
                    team = match.teamB,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
            ) {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = SPACING_4),
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = SPACING_16, vertical = SPACING_8),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(match.leagueImageUrl)
                            .build(),
                        contentDescription = stringResource(
                            R.string.team_logo_description,
                            match.league.orEmpty()
                        ),
                        modifier = Modifier
                            .size(SIZE_16)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(SIZE_8))
                    Text(
                        text = "${match.league} ${stringResource(R.string.league_separator)} ${match.serie}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun DateTag(
    modifier: Modifier = Modifier,
    match: MatchResponseDTO
) {
    val color = if (match.isLive == true) AccentRed else TextSecondary.copy(alpha = 0.2f)
    Box(
        modifier = modifier
            .background(
                color,
                shape = RoundedCornerShape(
                    topStart = SIZE_0,
                    topEnd = SIZE_8,
                    bottomStart = SIZE_16,
                    bottomEnd = SIZE_0
                )
            )
            .padding(horizontal = SPACING_8, vertical = SPACING_4)
    ) {
        if (match.isLive != true) {
            Text(
                text = match.formattedDateLabel.orEmpty(),
                style = MaterialTheme.typography.labelSmall,
                color = Color.White
            )
        } else {
            Text(
                text = stringResource(R.string.live_indicator),
                color = Color.White,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
