package com.gmribas.cstv.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import com.gmribas.cstv.repository.match.IMatchRepository
import com.gmribas.cstv.repository.dto.MatchResponseDTO
import javax.inject.Inject

class GetMatchesUseCase @Inject constructor(
    private val repository: IMatchRepository
) {

    operator fun invoke(): Flow<PagingData<MatchResponseDTO>> {
        return repository.getMatchesPagingFlow()
    }
}
