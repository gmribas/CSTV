package com.gmribas.cstv.ui.matches.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.gmribas.cstv.R
import com.gmribas.cstv.ui.theme.PlaceholderColor
import com.gmribas.cstv.ui.theme.SIZE_64
import kotlin.text.isNullOrBlank

@Composable
fun CustomImage(
    imageUrl: String?,
    teamName: String?,
    modifier: Modifier = Modifier,
    size: Dp = SIZE_64,
    placeholderColor: Color = PlaceholderColor,
    clipShape: RoundedCornerShape = CircleShape
) {
    val context = LocalContext.current

    val imageRequest = remember(imageUrl, context) {
        if (imageUrl.isNullOrBlank()) {
            null
        } else {
            ImageRequest.Builder(context)
                .data(imageUrl)
                .build()
        }
    }

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        if (imageRequest == null) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(clipShape)
                    .background(placeholderColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_team_placeholder),
                    contentDescription = stringResource(R.string.team_placeholder_description),
                    modifier = Modifier.size(size / 2),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
            }
        } else {
            AsyncImage(
                model = imageRequest,
                contentDescription = teamName?.let { stringResource(R.string.team_logo_description, it) } ?: stringResource(R.string.team_placeholder_description),
                modifier = Modifier
                    .matchParentSize()
                    .clip(clipShape),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.ic_team_placeholder),
                error = painterResource(id = R.drawable.ic_team_placeholder),
            )
        }
    }
}
