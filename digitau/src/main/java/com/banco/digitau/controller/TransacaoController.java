package com.banco.digitau.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banco.digitau.model.Transacao;
import com.banco.digitau.service.ListaTransacaoService;

@RestController
@RequestMapping("/transacao")
@CrossOrigin("*")
public class TransacaoController {

	@Autowired
	private ListaTransacaoService repository;
	
	@GetMapping
	public ResponseEntity<List<Transacao>> getAll(){
		return ResponseEntity.ok(repository.getTransacoes());
	}
	
	//Recebe a transação e faz a validação
	@PostMapping
	public ResponseEntity<Transacao> post(@Valid @RequestBody Transacao transacao) {
		//Adiciona a transação passada pelo corpo da requisição na lista de transações
		repository.add(transacao);
		
		//Retorna status CREATED com corpo vazio
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@DeleteMapping
	public ResponseEntity<Transacao> delete() {
		//Limpa todas as transações efetuadas
		repository.clear();
		
		//Retorna status OK com corpo vazio
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}