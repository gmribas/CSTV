package com.gmribas.cstv.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.gmribas.cstv.R
import com.gmribas.cstv.ui.theme.BackgroundDark
import com.gmribas.cstv.ui.theme.BackgroundLight
import com.gmribas.cstv.ui.theme.SIZE_120
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToMatches: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(1500)
        onNavigateToMatches()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        BackgroundDark,
                        BackgroundLight
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_fuze_logo),
            contentDescription = stringResource(R.string.cstv_logo_description),
            modifier = Modifier.size(SIZE_120)
        )
    }
}
