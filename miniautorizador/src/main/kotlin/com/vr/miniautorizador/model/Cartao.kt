package com.vr.miniautorizador.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Cartao (
    @Id
    val numeroCartao: Long,
    val senha: String,
    val saldo: Long? = 500
)