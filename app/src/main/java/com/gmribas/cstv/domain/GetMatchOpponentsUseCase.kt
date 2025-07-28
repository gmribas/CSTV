package com.gmribas.cstv.domain

import com.gmribas.cstv.repository.dto.MatchOpponentsResponseDTO
import com.gmribas.cstv.repository.match.IMatchRepository
import javax.inject.Inject

class GetMatchOpponentsUseCase @Inject constructor(
    private val repository: IMatchRepository
) {

    suspend operator fun invoke(slug: String): UseCaseResult<MatchOpponentsResponseDTO> {
        return try {
            UseCaseResult.Success(repository.getMatchOpponents(slug))
        } catch (e: java.net.UnknownHostException) {
            UseCaseResult.Error(Exception("Network connection failed. Please check your internet connection.", e))
        } catch (e: java.net.ConnectException) {
            UseCaseResult.Error(Exception("Could not connect to server. Please try again later.", e))
        } catch (e: java.net.SocketTimeoutException) {
            UseCaseResult.Error(Exception("Connection timeout. Please try again.", e))
        } catch (e: Throwable) {
            UseCaseResult.Error(e)
        }
    }
}
