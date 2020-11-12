package com.banco.digitau.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

import org.springframework.stereotype.Service;

import com.banco.digitau.model.Transacao;

@Service
public class ListaTransacaoService {
	
	//Criação de Lista que servirá como repositório de transações
	private List<Transacao> repository = new ArrayList<>();
	
	//Retorna a lista de transações
	public List<Transacao> getTransacoes() {
		return repository;
	}
	
	//Adiciona uma transação na lista
	public Transacao add(Transacao transacao) {
		repository.add(transacao);
		int transacaoIndex = repository.indexOf(transacao);
		return repository.get(transacaoIndex);
	}
	
	//Limpa a lista de transações
	public List<Transacao> clear() {
		repository.clear();
		return repository;
	}
	
	//Retorna as estatísticas das transações feitas no último minuto
	public DoubleSummaryStatistics getEstatistica() {
		
		//Instancia um objeto de estatística
		DoubleSummaryStatistics dss = new DoubleSummaryStatistics();
		
		//Filtra os valores que serão salvos no objeto DoubleSummaryStatistics
		//Apenas as transações efetuadas no último minuto serão salvas
		repository.stream().filter(repo -> repo.getDataHora().isAfter(OffsetDateTime.now().minusMinutes(1)))
				.forEach(valor -> dss.accept(valor.getValor()));
		
		return dss;
	}

}
