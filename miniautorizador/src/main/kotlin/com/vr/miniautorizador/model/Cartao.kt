package com.vr.miniautorizador.model

data class Cartoes (
    val id: String? = null,
    val numeroCartao: Int,
    val senha: String,
    val saldo: Long? = 500
)