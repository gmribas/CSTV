package com.gmribas.cstv.ui.matches

import androidx.paging.PagingData
import com.gmribas.cstv.domain.GetMatchesUseCase
import com.gmribas.cstv.repository.dto.MatchResponseDTO
import com.gmribas.cstv.ui.matches.model.MatchScreenEvent
import com.gmribas.cstv.ui.matches.model.MatchesScreenState
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MatchesScreenViewModelTest {

    private lateinit var viewModel: MatchesScreenViewModel
    private lateinit var getMatchesUseCase: GetMatchesUseCase
    private lateinit var dispatcher: TestDispatcher

    @Before
    fun setUp() {
        getMatchesUseCase = mockk()
        dispatcher = StandardTestDispatcher()
        Dispatchers.setMain(dispatcher)
        
        every { getMatchesUseCase() } returns flowOf(PagingData.empty())
        viewModel = MatchesScreenViewModel(getMatchesUseCase)
    }

    @Test
    fun `initial state should be success with paging flow`() = runTest {
        // Given
        val initialState = viewModel.state.value

        // When & Then
        assert(initialState is MatchesScreenState.MatchesScreenSuccessState)
        val successState = initialState as MatchesScreenState.MatchesScreenSuccessState
        assert(successState.matchesPagingFlow != null)
    }

    @Test
    fun `onEvent LoadRecentMatches should not change state`() = runTest {
        // Given
        val initialState = viewModel.state.value

        // When
        viewModel.onEvent(MatchScreenEvent.LoadRecentMatches)

        // Then
        assertEquals(initialState, viewModel.state.value)
    }

    @Test
    fun `onEvent LoadMoreMatches should not change state`() = runTest {
        // Given
        val initialState = viewModel.state.value

        // When
        viewModel.onEvent(MatchScreenEvent.LoadMoreMatches)

        // Then
        assertEquals(initialState, viewModel.state.value)
    }

    @Test
    fun `onEvent ForceRefresh should not change state`() = runTest {
        // Given
        val initialState = viewModel.state.value

        // When
        viewModel.onEvent(MatchScreenEvent.ForceRefresh)

        // Then
        assertEquals(initialState, viewModel.state.value)
    }
}
