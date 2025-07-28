package com.gmribas.cstv.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.gmribas.cstv.R
import com.gmribas.cstv.repository.dto.TeamDTO
import com.gmribas.cstv.ui.theme.SIZE_8
import com.gmribas.cstv.ui.theme.SIZE_48
import com.gmribas.cstv.ui.theme.SIZE_72

@Composable
fun TeamColumn(team: TeamDTO?, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.width(SIZE_72)
    ) {
        team?.imageUrl?.takeIf { it.isNotBlank() }?.let { imageUrl ->
            CustomImage(
                imageUrl = imageUrl,
                teamName = team.name,
                size = SIZE_48
            )
        }

        Spacer(modifier = Modifier.height(SIZE_8))

        Text(
            text = team?.name.orEmpty(),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.White
        )
    }
}
