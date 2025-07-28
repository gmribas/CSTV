package com.gmribas.cstv.domain

import android.util.Log
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
            Log.e("GetMatchOpponentsUseCase", "Network connection failed for slug: $slug", e)
            UseCaseResult.Error(Exception("Network connection failed. Please check your internet connection.", e))
        } catch (e: java.net.ConnectException) {
            Log.e("GetMatchOpponentsUseCase", "Could not connect to server for slug: $slug", e)
            UseCaseResult.Error(Exception("Could not connect to server. Please try again later.", e))
        } catch (e: java.net.SocketTimeoutException) {
            Log.e("GetMatchOpponentsUseCase", "Connection timeout for slug: $slug", e)
            UseCaseResult.Error(Exception("Connection timeout. Please try again.", e))
        } catch (e: Throwable) {
            Log.e("GetMatchOpponentsUseCase", "Unexpected error for slug: $slug", e)
            UseCaseResult.Error(e)
        }
    }
}
