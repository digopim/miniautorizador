package com.vr.miniautorizador.repository

import com.vr.miniautorizador.model.Cartao
import org.springframework.data.mongodb.repository.MongoRepository

interface CartaoRepository: MongoRepository <Cartao, Long> {

}