package com.vr.miniautorizador.controller

import com.vr.miniautorizador.model.Cartoes
import com.vr.miniautorizador.repository.CartoesRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("cartoes")
class CartoesController (val repository: CartoesRepository){

    @PostMapping
    fun create(@RequestBody cartao: Cartoes): ResponseEntity<Cartoes> {
        val cartaoSalvo = repository.save(cartao)
        return ResponseEntity.ok(cartaoSalvo)
    }

    @GetMapping("{numerocartao}")
    fun read(@PathVariable numerocartao: Int) = ResponseEntity.ok(repository.findByNumeroCartao(numerocartao))

//    @PutMapping("{numeroCartao}")
//    fun update(@PathVariable numeroCartao: Int, @RequestBody cartao: Cartoes) : ResponseEntity<Cartoes> {
//
//        repository.findByNumeroCartao(numeroCartao)
//
//        val cartaoDBOptional = repository.findByDocument(numeroCartao)
//        val cartaoDB =  cartaoDBOptional.orElseThrow{RuntimeException("$numeroCartao")}
//        val saved = repository.save(cartaoDB.copy(saldo = cartao.saldo))
//        return ResponseEntity.ok(saved)
//    }

}