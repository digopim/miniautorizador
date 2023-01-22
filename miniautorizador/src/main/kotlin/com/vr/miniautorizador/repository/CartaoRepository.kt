package com.vr.miniautorizador.repository

import com.vr.miniautorizador.model.Cartoes
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.Optional

interface CartoesRepository: MongoRepository <Cartoes, String> {
    fun findByNumeroCartao(document: Int): Optional<Cartoes>
}