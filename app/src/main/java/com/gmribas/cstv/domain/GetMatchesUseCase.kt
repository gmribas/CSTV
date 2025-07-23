package com.gmribas.cstv.domain

import com.gmribas.cstv.repository.match.IMatchRepository
import com.gmribas.cstv.repository.dto.MatchResponseDTO
import javax.inject.Inject

class GetMatchesUseCase @Inject constructor(
    private val repository: IMatchRepository
) {

    suspend operator fun invoke(beginAt: String, page: Int): UseCaseResult<List<MatchResponseDTO>> {
        return try {
            UseCaseResult.Success(repository.getMatches(beginAt, page))
        } catch (e: Throwable) {
            UseCaseResult.Error(e)
        }
    }
}
