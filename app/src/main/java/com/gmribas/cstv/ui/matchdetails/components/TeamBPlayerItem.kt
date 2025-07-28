package com.gmribas.cstv.ui.matchdetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.gmribas.cstv.repository.dto.PlayerDetailsDTO
import com.gmribas.cstv.ui.theme.SIZE_0
import com.gmribas.cstv.ui.theme.SIZE_12
import com.gmribas.cstv.ui.theme.SIZE_48
import com.gmribas.cstv.ui.theme.SIZE_64
import com.gmribas.cstv.ui.theme.SPACING_4
import com.gmribas.cstv.ui.theme.SPACING_16

@Composable
fun TeamBPlayerItem(
    modifier: Modifier = Modifier,
    player: PlayerDetailsDTO?
) {
    if (player == null) {
        // Empty placeholder for missing player
        Box(
            modifier = modifier
                .height(SIZE_48)
                .clip(RoundedCornerShape(
                    topStart = SIZE_12,
                    topEnd = SIZE_0,
                    bottomEnd = SIZE_0,
                    bottomStart = SIZE_12
                ))
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
        )
    } else {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(
                    topStart = SIZE_12,
                    topEnd = SIZE_0,
                    bottomEnd = SIZE_0,
                    bottomStart = SIZE_12
                ))
                .background(MaterialTheme.colorScheme.surface)
                .padding(SPACING_16)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Nickname (center)
                Text(
                    text = player.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(SPACING_4))
                
                // Player name (below nickname)
                Text(
                    text = player.firstName?.let { first ->
                        player.lastName?.let { last -> "$first $last" } ?: first
                    } ?: player.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
