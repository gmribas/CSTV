package com.gmribas.cstv.domain

import com.gmribas.cstv.repository.dto.MatchOpponentsResponseDTO
import com.gmribas.cstv.repository.dto.TeamDetailsDTO
import com.gmribas.cstv.repository.match.IMatchRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class GetMatchOpponentsUseCaseTest {

    private lateinit var repository: IMatchRepository
    private lateinit var useCase: GetMatchOpponentsUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetMatchOpponentsUseCase(repository)
    }

    @Test
    fun `invoke should return success when repository returns data`() = runTest {
        // Given
        val slug = "test-match"
        val expectedResponse = MatchOpponentsResponseDTO(
            opponents = listOf(
                TeamDetailsDTO(
                    id = 1L,
                    name = "Team A",
                    slug = "team-a",
                    acronym = "TA",
                    imageUrl = "https://example.com/team-a.png",
                    players = emptyList()
                )
            )
        )
        
        coEvery { repository.getMatchOpponents(slug) } returns expectedResponse

        // When
        val result = useCase(slug)

        // Then
        assert(result is UseCaseResult.Success)
        assertEquals(expectedResponse, (result as UseCaseResult.Success).data)
        coVerify { repository.getMatchOpponents(slug) }
    }

    @Test
    fun `invoke should return error with custom message for UnknownHostException`() = runTest {
        // Given
        val slug = "test-match"
        val originalException = UnknownHostException("No internet")
        coEvery { repository.getMatchOpponents(slug) } throws originalException

        // When
        val result = useCase(slug)

        // Then
        assert(result is UseCaseResult.Error)
        val error = (result as UseCaseResult.Error).error
        assertEquals("Network connection failed. Please check your internet connection.", error.message)
        assertEquals(originalException, error.cause)
        coVerify { repository.getMatchOpponents(slug) }
    }

    @Test
    fun `invoke should return error with custom message for ConnectException`() = runTest {
        // Given
        val slug = "test-match"
        val originalException = ConnectException("Connection refused")
        coEvery { repository.getMatchOpponents(slug) } throws originalException

        // When
        val result = useCase(slug)

        // Then
        assert(result is UseCaseResult.Error)
        val error = (result as UseCaseResult.Error).error
        assertEquals("Could not connect to server. Please try again later.", error.message)
        assertEquals(originalException, error.cause)
        coVerify { repository.getMatchOpponents(slug) }
    }

    @Test
    fun `invoke should return error with custom message for SocketTimeoutException`() = runTest {
        // Given
        val slug = "test-match"
        val originalException = SocketTimeoutException("Read timeout")
        coEvery { repository.getMatchOpponents(slug) } throws originalException

        // When
        val result = useCase(slug)

        // Then
        assert(result is UseCaseResult.Error)
        val error = (result as UseCaseResult.Error).error
        assertEquals("Connection timeout. Please try again.", error.message)
        assertEquals(originalException, error.cause)
        coVerify { repository.getMatchOpponents(slug) }
    }

    @Test
    fun `invoke should return error for other throwables`() = runTest {
        // Given
        val slug = "test-match"
        val originalException = RuntimeException("Some other error")
        coEvery { repository.getMatchOpponents(slug) } throws originalException

        // When
        val result = useCase(slug)

        // Then
        assert(result is UseCaseResult.Error)
        val error = (result as UseCaseResult.Error).error
        assertEquals(originalException, error)
        coVerify { repository.getMatchOpponents(slug) }
    }
}
