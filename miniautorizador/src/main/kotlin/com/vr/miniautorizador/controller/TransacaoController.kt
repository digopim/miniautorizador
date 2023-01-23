package com.vr.miniautorizador.controller

import com.vr.miniautorizador.model.Cartao
import com.vr.miniautorizador.model.Transacao
import com.vr.miniautorizador.repository.CartaoRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transacoes")
class TransacaoController (val repository: CartaoRepository){

    @PutMapping
    fun update(@RequestBody transacao: Transacao): ResponseEntity<Cartao> {
        return if(validate(transacao)) {
            val cartao = repository.findById(transacao.numeroCartao).get()
            ResponseEntity.ok(repository.save(cartao.copy(saldo = cartao.saldo?.minus(transacao.valor))))
        } else {
            ResponseEntity.unprocessableEntity().build()
        }
    }

    fun validate(transacao: Transacao): Boolean =
        repository.existsById(transacao.numeroCartao) &&
        authenticated(transacao.numeroCartao, transacao.senhaCartao) &&
        authorized(repository.findById(transacao.numeroCartao).get(), transacao.valor)

    fun authenticated(numeroCartao: Long, senha: String): Boolean = repository.findById(numeroCartao).map(Cartao::senha).get() == senha

    fun authorized(cartao: Cartao, valor: Long): Boolean = cartao.saldo?.minus(valor)!! >= 0L

}