package com.gmribas.cstv.ui.matches

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.activity.compose.BackHandler
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.gmribas.cstv.R
import com.gmribas.cstv.repository.dto.MatchResponseDTO
import com.gmribas.cstv.ui.common.ErrorContent
import com.gmribas.cstv.ui.common.LoadingContent
import com.gmribas.cstv.ui.matches.components.MatchItem
import com.gmribas.cstv.ui.matches.model.MatchScreenEvent
import com.gmribas.cstv.ui.matches.model.MatchesScreenState
import com.gmribas.cstv.ui.theme.AccentRed
import com.gmribas.cstv.ui.theme.SPACING_4
import com.gmribas.cstv.ui.theme.SPACING_16
import com.gmribas.cstv.ui.theme.SPACING_20
import com.gmribas.cstv.ui.theme.SPACING_24

@Composable
fun MatchesScreen(
    state: MatchesScreenState,
    onEvent: (MatchScreenEvent) -> Unit,
    onItemClick: (MatchResponseDTO) -> Unit,
    onFinish: () -> Unit,
) {
    BackHandler {
        onFinish()
    }
    
    LaunchedEffect(Unit) {
        onEvent(MatchScreenEvent.LoadRecentMatches)
    }
    
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = SPACING_24, vertical = SPACING_20)
            ) {
                Text(
                    text = stringResource(R.string.matches_title),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            
            when (state) {
            is MatchesScreenState.MatchesScreenLoadingState -> {
                LoadingContent(modifier = Modifier.fillMaxSize())
            }
            
            is MatchesScreenState.MatchesScreenErrorState -> {
                val errorState = state as MatchesScreenState.MatchesScreenErrorState
                ErrorContent(
                    error = errorState.error,
                    modifier = Modifier.fillMaxSize()
                )
            }
            
            is MatchesScreenState.MatchesScreenSuccessState -> {
                val successState = state as MatchesScreenState.MatchesScreenSuccessState
                val matchesPagingItems = successState.matchesPagingFlow.collectAsLazyPagingItems()
                
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(SPACING_16),
                    contentPadding = PaddingValues(horizontal = SPACING_16),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        count = matchesPagingItems.itemCount,
                        key = { index -> matchesPagingItems[index]?.slug ?: index }
                    ) { index ->
                        val match = matchesPagingItems[index]
                        match?.let {
                            MatchItem(
                                match = it,
                                onClick = { onItemClick(it) }
                            )
                        }
                    }
                    
                    // Handle loading states
                    matchesPagingItems.apply {
                        when {
                            loadState.refresh is LoadState.Loading -> {
                                item {
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator(color = AccentRed)
                                    }
                                }
                            }
                            loadState.append is LoadState.Loading -> {
                                item {
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator(color = AccentRed)
                                    }
                                }
                            }
                            loadState.refresh is LoadState.Error -> {
                                val error = loadState.refresh as LoadState.Error
                                item {
                                    Text(
                                        text = stringResource(R.string.error_prefix) + error.error.localizedMessage,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.padding(SPACING_16)
                                    )
                                }
                            }
                            loadState.append is LoadState.Error -> {
                                val error = loadState.append as LoadState.Error
                                item {
                                    Text(
                                        text = stringResource(R.string.error_prefix) + error.error.localizedMessage,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.padding(SPACING_16)
                                    )
                                }
                            }
                        }
                    }
                }
            }
            
            is MatchesScreenState.MatchesScreenIdleState -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.welcome_message),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(SPACING_16)
                    )
                }
            }
            }
        }
    }
}
