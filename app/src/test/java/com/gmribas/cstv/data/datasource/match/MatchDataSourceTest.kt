package com.gmribas.cstv.data.datasource.match

import com.gmribas.cstv.data.api.PandascoreApi
import com.gmribas.cstv.data.model.MatchResponse
import com.gmribas.cstv.data.model.MatchOpponentsResponse
import com.gmribas.cstv.data.model.TeamDetailsResponse
import com.gmribas.cstv.data.model.TeamPlayer
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.SocketTimeoutException

class MatchDataSourceTest {

    private lateinit var api: PandascoreApi
    private lateinit var dataSource: MatchDataSource

    @Before
    fun setUp() {
        api = mockk()
        dataSource = MatchDataSource(api)
    }

    @Test
    fun `getOrderedMatches should return sorted matches with running matches first`() = runTest {
        // Given
        val runningMatch = createMatchResponse("match1", "running", "2024-01-01T10:00:00Z")
        val upcomingMatch = createMatchResponse("match2", "upcoming", "2024-01-01T12:00:00Z")
        val liveMatch = createMatchResponse("match3", "live", "2024-01-01T11:00:00Z")
        
        coEvery { api.getRunningMatches(page = 1, perPage = 25) } returns listOf(runningMatch, liveMatch)
        coEvery { api.getUpcomingMatches(page = 1, perPage = 25) } returns listOf(upcomingMatch)

        // When
        val result = dataSource.getOrderedMatches("2024-01-01T00:00:00Z", 1)

        // Then
        assertEquals(3, result.size)
        assertEquals("running", result[0].status)
        assertEquals("live", result[1].status)
        assertEquals("upcoming", result[2].status)
        
        coVerify { api.getRunningMatches(page = 1, perPage = 25) }
        coVerify { api.getUpcomingMatches(page = 1, perPage = 25) }
    }

    @Test
    fun `getOrderedMatches should handle exception when API calls fail`() = runTest {
        // Given
        coEvery { api.getRunningMatches(any(), any()) } throws SocketTimeoutException("Connection timeout")
        coEvery { api.getUpcomingMatches(any(), any()) } returns emptyList()

        // When
        try {
            dataSource.getOrderedMatches("2024-01-01T00:00:00Z", 1)
            assert(false)
        } catch (e: SocketTimeoutException) {
            // Then
            assertEquals("Connection timeout", e.message)
        }
    }

    @Test
    fun `getMatchOpponents should return opponents successfully`() = runTest {
        // Given
        val slug = "test-match"
        val expectedResponse = MatchOpponentsResponse(
            opponentType = "Team",
            opponents = listOf(
                TeamDetailsResponse(
                    id = 1L,
                    acronym = "TA",
                    imageUrl = "https://example.com/team-a.png",
                    location = null,
                    name = "Team A",
                    players = emptyList(),
                    slug = "team-a",
                    modifiedAt = null
                )
            )
        )
        
        coEvery { api.getMatchOpponents(slug) } returns expectedResponse

        // When
        val result = dataSource.getMatchOpponents(slug)

        // Then
        assertEquals(expectedResponse, result)
        coVerify { api.getMatchOpponents(slug) }
    }

    @Test
    fun `getMatchOpponents should propagate exception when API call fails`() = runTest {
        // Given
        val slug = "test-match"
        val exception = RuntimeException("API Error")
        coEvery { api.getMatchOpponents(slug) } throws exception

        // When
        try {
            dataSource.getMatchOpponents(slug)
            assert(false)
        } catch (e: RuntimeException) {
            // Then
            assertEquals("API Error", e.message)
        }
    }

    private fun createMatchResponse(slug: String, status: String, beginAt: String) = MatchResponse(
        slug = slug,
        status = status,
        beginAt = beginAt,
        games = null,
        gameAdvantage = null,
        opponents = null,
        draw = false,
        winnerType = null,
        league = null,
        videogameTitle = null,
        detailedStats = false,
        matchType = null,
        videogame = null,
        rescheduled = false,
        numberOfGames = null,
        winner = null,
        forfeit = false,
        serieId = null,
        modifiedAt = null,
        originalScheduledAt = null,
        results = null,
        id = null,
        serie = null,
        live = null,
        name = null,
        videogameVersion = null,
        streamsList = null,
        tournamentId = null,
        scheduledAt = null,
        winnerId = null,
        endAt = null,
        leagueId = null,
        tournament = null
    )
}
