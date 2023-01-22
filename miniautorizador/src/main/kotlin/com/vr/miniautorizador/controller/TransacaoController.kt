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
    fun update(@RequestBody transacao: Transacao)  : ResponseEntity<Cartao> {
        val cartaoDB = repository.findById(transacao.numeroCartao);
        return if(cartaoDB.isPresent){
            val novoSaldo = cartaoDB.get().saldo?.minus(transacao.valor)
            val aux = cartaoDB.get().copy(saldo = novoSaldo)
            ResponseEntity.ok(repository.save(aux))
        } else {
            ResponseEntity.unprocessableEntity().build()
        }
    }


}