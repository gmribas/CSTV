package com.gmribas.cstv.core

interface IMapper<in DOMAIN: Any, out DTO: Any> {
    fun toDTO(model: DOMAIN): DTO
}
