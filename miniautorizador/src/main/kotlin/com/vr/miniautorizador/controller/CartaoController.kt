package com.vr.miniautorizador.controller

import com.vr.miniautorizador.model.Cartao
import com.vr.miniautorizador.repository.CartaoRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/cartoes")
public class CartaoController (val repository: CartaoRepository){

    @PostMapping
    fun create(@RequestBody cartao: Cartao) : ResponseEntity<Cartao> {
        return if(exists(cartao.numeroCartao)) {
            ResponseEntity.unprocessableEntity().body(Cartao(cartao.numeroCartao, cartao.senha))
        } else {
            ResponseEntity.created(URI.create("Sucesso")).body(repository.save(Cartao(cartao.numeroCartao, cartao.senha)))
        }
    }
    @GetMapping()
    fun read() = ResponseEntity.ok(repository.findAll())

    @GetMapping("/{id}")
    fun readbyId(@PathVariable id: Long) : ResponseEntity<Long> {
        val cartao = repository.findById(id);
        return if(cartao.isPresent){
            ResponseEntity.ok(repository.findById(id).get().checkSaldo())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    fun exists(numeroCartao: Long): Boolean = repository.existsById(numeroCartao)

}