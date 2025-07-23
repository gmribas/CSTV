package com.gmribas.cstv.domain

sealed interface UseCaseResult<out T : Any> {
    class Success<out T : Any>(data: T) : UseCaseResult<T>
    class Error<out T : Any>(e: Throwable) : UseCaseResult<T>
}
