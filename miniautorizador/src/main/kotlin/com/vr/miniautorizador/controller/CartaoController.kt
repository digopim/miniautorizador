package com.vr.miniautorizador.controller

import com.vr.miniautorizador.model.Cartao
import com.vr.miniautorizador.repository.CartaoRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/cartoes")
public class CartaoController (val repository: CartaoRepository){

    @PostMapping
    fun create(@RequestBody cartao: Cartao) : ResponseEntity<Cartao> {
        return if(repository.existsById(cartao.numeroCartao)){
            ResponseEntity.unprocessableEntity().body(cartao)
        } else {
            ResponseEntity.ok(repository.save(cartao))
        }
    }
    @GetMapping()
    fun read() = ResponseEntity.ok(repository.findAll())

    @GetMapping("/{id}")
    fun readbyId(@PathVariable id: Long) : ResponseEntity<Cartao> {
        val cartao = repository.findById(id);
        return if(cartao.isPresent){
            ResponseEntity.ok(repository.findById(id).get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

}