package com.gmribas.cstv.repository.match

import androidx.paging.PagingData
import com.gmribas.cstv.core.IMapper
import com.gmribas.cstv.data.datasource.match.IMatchDataSource
import com.gmribas.cstv.data.model.MatchOpponentsResponse
import com.gmribas.cstv.data.model.MatchResponse
import com.gmribas.cstv.data.model.TeamDetailsResponse
import com.gmribas.cstv.repository.dto.MatchOpponentsResponseDTO
import com.gmribas.cstv.repository.dto.MatchResponseDTO
import com.gmribas.cstv.repository.dto.TeamDetailsDTO
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class MatchRepositoryTest {

    private lateinit var dataSource: IMatchDataSource
    private lateinit var mapper: IMapper<MatchResponse, MatchResponseDTO>
    private lateinit var matchOpponentsMapper: IMapper<MatchOpponentsResponse, MatchOpponentsResponseDTO>
    private lateinit var repository: MatchRepository

    @Before
    fun setUp() {
        dataSource = mockk()
        mapper = mockk()
        matchOpponentsMapper = mockk()
        repository = MatchRepository(dataSource, mapper, matchOpponentsMapper)
    }

    @Test
    fun `getMatchesPagingFlow should return mapped paging data successfully`() = runTest {
        // Given & When & Then
        repository.getMatchesPagingFlow()
        
    }

    @Test
    fun `getMatchOpponents should return mapped DTO successfully`() = runTest {
        // Given
        val slug = "test-match"
        val teamDetailsResponse = TeamDetailsResponse(
            id = 1L,
            acronym = "TA",
            imageUrl = "https://example.com/team-a.png",
            location = null,
            name = "Team A",
            players = emptyList(),
            slug = "team-a",
            modifiedAt = null
        )
        
        val matchOpponentsResponse = MatchOpponentsResponse(
            opponentType = "Team",
            opponents = listOf(teamDetailsResponse)
        )
        
        val expectedDTO = MatchOpponentsResponseDTO(
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

        coEvery { dataSource.getMatchOpponents(slug) } returns matchOpponentsResponse
        every { matchOpponentsMapper.toDTO(matchOpponentsResponse) } returns expectedDTO

        // When
        val result = repository.getMatchOpponents(slug)

        // Then
        assertEquals(expectedDTO, result)
        coVerify { dataSource.getMatchOpponents(slug) }
        verify { matchOpponentsMapper.toDTO(matchOpponentsResponse) }
    }

    @Test
    fun `getMatchOpponents should propagate exception when data source fails`() = runTest {
        // Given
        val slug = "test-match"
        val exception = UnknownHostException("Network error")
        coEvery { dataSource.getMatchOpponents(slug) } throws exception

        // When
        try {
            repository.getMatchOpponents(slug)
            assert(false)
        } catch (e: UnknownHostException) {
            // Then
            assertEquals("Network error", e.message)
            coVerify { dataSource.getMatchOpponents(slug) }
        }
    }

    @Test
    fun `getMatchOpponents should propagate exception when mapper fails`() = runTest {
        // Given
        val slug = "test-match"
        val matchOpponentsResponse = MatchOpponentsResponse(
            opponentType = "Team",
            opponents = emptyList()
        )
        val mapperException = RuntimeException("Mapping error")
        
        coEvery { dataSource.getMatchOpponents(slug) } returns matchOpponentsResponse
        every { matchOpponentsMapper.toDTO(matchOpponentsResponse) } throws mapperException

        // When
        try {
            repository.getMatchOpponents(slug)
            assert(false)
        } catch (e: RuntimeException) {
            // Then
            assertEquals("Mapping error", e.message)
            coVerify { dataSource.getMatchOpponents(slug) }
            verify { matchOpponentsMapper.toDTO(matchOpponentsResponse) }
        }
    }
}
