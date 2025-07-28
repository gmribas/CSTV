package com.gmribas.cstv.domain

import androidx.paging.PagingData
import com.gmribas.cstv.repository.dto.MatchResponseDTO
import com.gmribas.cstv.repository.match.IMatchRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetMatchesUseCaseTest {

    private lateinit var repository: IMatchRepository
    private lateinit var useCase: GetMatchesUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetMatchesUseCase(repository)
    }

    @Test
    fun `invoke should return paging flow from repository`() = runTest {
        // Given
        val expectedFlow: Flow<PagingData<MatchResponseDTO>> = flowOf(PagingData.empty())
        every { repository.getMatchesPagingFlow() } returns expectedFlow

        // When
        val result = useCase()

        // Then
        assert(result === expectedFlow)
        verify { repository.getMatchesPagingFlow() }
    }
}
