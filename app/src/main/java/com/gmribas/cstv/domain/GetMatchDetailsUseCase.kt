package com.gmribas.cstv.domain

import com.gmribas.cstv.repository.dto.MatchDetailsResponseDTO
import com.gmribas.cstv.repository.match.IMatchRepository
import javax.inject.Inject

class GetMatchDetailsUseCase @Inject constructor(
    private val repository: IMatchRepository
) {

    suspend operator fun invoke(slug: String): UseCaseResult<MatchDetailsResponseDTO> {
        return try {
            UseCaseResult.Success(repository.getMatchDetails(slug))
        } catch (e: Throwable) {
            UseCaseResult.Error(e)
        }
    }
}
