package com.gmribas.cstv.ui.matches

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.ui.Alignment
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.activity.compose.BackHandler
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.gmribas.cstv.R
import com.gmribas.cstv.repository.dto.MatchResponseDTO
import com.gmribas.cstv.ui.common.ErrorContent
import com.gmribas.cstv.ui.common.LoadingContent
import com.gmribas.cstv.ui.matches.components.MatchItem
import com.gmribas.cstv.ui.matches.model.MatchScreenEvent
import com.gmribas.cstv.ui.matches.model.MatchesListScreenState
import com.gmribas.cstv.ui.theme.AccentRed
import com.gmribas.cstv.ui.theme.SPACING_4
import com.gmribas.cstv.ui.theme.SPACING_16
import com.gmribas.cstv.ui.theme.SPACING_20
import com.gmribas.cstv.ui.theme.SPACING_24

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchesListScreen(
    state: MatchesListScreenState,
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
    
    Box(
        modifier = Modifier
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
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            
            when (state) {
                is MatchesListScreenState.MatchesScreenLoadingState -> {
                    LoadingContent(modifier = Modifier.fillMaxSize())
                }
                
                is MatchesListScreenState.MatchesScreenErrorState -> {
                    val errorState = state as MatchesListScreenState.MatchesScreenErrorState
                    PullToRefreshBox(
                        isRefreshing = false,
                        onRefresh = {
                            onEvent(MatchScreenEvent.ForceRefresh)
                        },
                        modifier = Modifier.fillMaxSize()
                    ) {
                        ErrorContent(
                            error = errorState.error,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
                
                is MatchesListScreenState.MatchesScreenSuccessState -> {
                    val successState = state as MatchesListScreenState.MatchesScreenSuccessState
                    val matchesPagingItems = successState.matchesPagingFlow.collectAsLazyPagingItems()
                    
                    var isUserRefreshing by remember { mutableStateOf(false) }
                    
                    LaunchedEffect(matchesPagingItems.loadState.refresh) {
                        if (matchesPagingItems.loadState.refresh !is LoadState.Loading) {
                            isUserRefreshing = false
                        }
                    }
                    
                    PullToRefreshBox(
                        isRefreshing = isUserRefreshing && matchesPagingItems.loadState.refresh is LoadState.Loading,
                        onRefresh = {
                            isUserRefreshing = true
                            onEvent(MatchScreenEvent.ForceRefresh)
                            matchesPagingItems.refresh()
                        },
                        modifier = Modifier.fillMaxSize()
                    ) {
                        when (matchesPagingItems.loadState.refresh) {
                            is LoadState.Loading -> {
                                LoadingContent(modifier = Modifier.fillMaxSize())
                            }
                            is LoadState.Error -> {
                                val error = matchesPagingItems.loadState.refresh as LoadState.Error
                                ErrorContent(
                                    error = stringResource(R.string.error_prefix) + error.error.localizedMessage,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            else -> {
                                when(matchesPagingItems.loadState.append) {
                                    is LoadState.Loading -> {
                                            LoadingContent(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                            )
                                    }
                                    is LoadState.Error -> {
                                        val error = matchesPagingItems.loadState.append as LoadState.Error
                                        ErrorContent(
                                            error = stringResource(R.string.error_prefix) + error.error.localizedMessage,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    }
                                    else -> {}
                                }

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
                                }
                            }
                        }
                    }
                }
                
                is MatchesListScreenState.MatchesScreenIdleState -> {}
            }
        }
    }
}
