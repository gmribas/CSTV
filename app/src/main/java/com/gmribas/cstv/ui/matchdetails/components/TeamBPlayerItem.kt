package com.gmribas.cstv.ui.matchdetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.text.style.TextOverflow
import com.gmribas.cstv.repository.dto.PlayerDetailsDTO
import com.gmribas.cstv.ui.common.CustomImage
import com.gmribas.cstv.ui.theme.ALPHA_0_3
import com.gmribas.cstv.ui.theme.SIZE_0
import com.gmribas.cstv.ui.theme.SIZE_4
import com.gmribas.cstv.ui.theme.SIZE_12
import com.gmribas.cstv.ui.theme.SIZE_36
import com.gmribas.cstv.ui.theme.SIZE_48
import com.gmribas.cstv.ui.theme.SIZE_52
import com.gmribas.cstv.ui.theme.OFFSET_4
import com.gmribas.cstv.ui.theme.OFFSET_12
import com.gmribas.cstv.ui.theme.SPACING_4
import com.gmribas.cstv.ui.theme.SPACING_8
import com.gmribas.cstv.ui.theme.SPACING_12
import com.gmribas.cstv.ui.theme.SPACING_16

private fun getTeamBPlayerShape() = RoundedCornerShape(
    topStart = SIZE_12,
    topEnd = SIZE_0,
    bottomEnd = SIZE_0,
    bottomStart = SIZE_12
)

@Composable
fun TeamBPlayerItem(
    modifier: Modifier = Modifier,
    player: PlayerDetailsDTO?
) {
    if (player == null) {
        Box(
            modifier = modifier
                .height(SIZE_52)
                .clip(getTeamBPlayerShape())
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = ALPHA_0_3))
        )
    } else {
        Box(
            modifier = modifier
            .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .offset(y = SPACING_4)
                    .clip(getTeamBPlayerShape())
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(start = SIZE_48 + SPACING_16, end = SPACING_16, top = SPACING_8, bottom = SPACING_8)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = player.name.orEmpty(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(SPACING_4))
                
                Text(
                    text = player.getDisplayName(),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            
            CustomImage(
                imageUrl = player.imageUrl,
                teamName = player.name,
                size = SIZE_48,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(y = -SPACING_4)
                    .padding(start = SPACING_8),
                clipShape = RoundedCornerShape(SIZE_4)
            )
        }
    }
}
