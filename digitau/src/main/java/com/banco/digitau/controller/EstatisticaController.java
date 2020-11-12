package com.banco.digitau.controller;

import java.util.DoubleSummaryStatistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banco.digitau.service.ListaTransacaoService;

@RestController
@RequestMapping("/estatistica")
@CrossOrigin("*")
public class EstatisticaController {
	
	@Autowired
	private ListaTransacaoService repository;
	
	@GetMapping
	public ResponseEntity<DoubleSummaryStatistics> get() {
		
		//Retorna o objeto de estatística pelo corpo JSON
		return ResponseEntity.status(HttpStatus.OK).body(repository.getEstatistica());
	}

}