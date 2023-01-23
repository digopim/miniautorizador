package com.vr.miniautorizador.controller

import com.vr.miniautorizador.model.Cartao
import com.vr.miniautorizador.model.Transacao
import com.vr.miniautorizador.repository.CartaoRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/transacoes")
class TransacaoController (val repository: CartaoRepository){

    @PutMapping
    fun update(@RequestBody transacao: Transacao): ResponseEntity<String> {
        val validate = validate(transacao)
        return if(validate.isNullOrEmpty()) {
            val cartao = repository.findById(transacao.numeroCartao).get()
            repository.save(cartao.copy(saldo = cartao.checkSaldo()?.minus(transacao.valor)))
            ResponseEntity.created(URI.create("Sucesso")).body("")
        } else {
            ResponseEntity.unprocessableEntity().body(validate.toString())
        }
    }

    fun validate(transacao: Transacao): ArrayList<String?> {
        val validate = arrayListOf<String?>()
        if(exists(transacao.numeroCartao)!!.not()) validate.add("CARTAO_INEXISTENTE") else
        if(authenticated(transacao.numeroCartao, transacao.senhaCartao)!!.not()) validate.add("SENHA_INVALIDA") else
        if(authorized(repository.findById(transacao.numeroCartao).get(), transacao.valor)!!.not()) validate.add("SALDO_INSUFICIENTE")
        return validate
    }
    fun exists(numeroCartao: Long): Boolean? = repository.existsById(numeroCartao)
    fun authenticated(numeroCartao: Long, senha: String): Boolean? = repository.findById(numeroCartao).map(Cartao::senha).get() == senha
    fun authorized(cartao: Cartao, valor: Long): Boolean? = cartao.checkSaldo()?.minus(valor)!! >= 0L
}