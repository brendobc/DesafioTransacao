package com.banco.digitau.controller;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.banco.digitau.model.Transacao;
import com.banco.digitau.service.ListaTransacaoService;
import com.google.gson.Gson;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@WebMvcTest
class TransacaoControllerTest {
	
	@Autowired
	private TransacaoController transacaoController;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ListaTransacaoService listaTransacaoService;
	
	@BeforeEach
	public void setup() {
		RestAssuredMockMvc.standaloneSetup(transacaoController);
	}
	
	@Test
	public void deveRetornarSucesso_QuandoCriarTransacao() throws Exception {
		
		//Instanciação do objeto do tipo Transacao que será enviado no corpo da requisição 'post'
		Transacao transacao = new Transacao();
		transacao.setValor(2);
		transacao.setDataHora(OffsetDateTime.now());
		
		//Instanciação de objetos para mandar um objeto JSON no teste da requisição 'post'
		Gson gson = new Gson();
		String transacaoJson = new String();
		
		//Implementação do 'listaTransacaoService' para ser utilizado pelo método 'post' de 'TransacaoController'
		Mockito
			.when(this.listaTransacaoService.add(transacao)).thenReturn(transacao);
		
		//Montagem do objeto 'transacao' em JSON
		//Isto é feito excepcionalmente porque o Gson converte o atributo 'dataHora' de uma forma que
		//a API não consegue ler posteriormente
		transacaoJson = "{\"valor\":" + gson.toJson(transacao.getValor()) + ",";
		transacaoJson += "\"dataHora\":" + gson.toJson(transacao.getDataHora().toString()) + "}";
		
		//Requisição sem erros esperando receber status http 201 - Created
		mockMvc.perform(MockMvcRequestBuilders.post("/transacao")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(transacaoJson))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	public void deveRetornarBadRequest_QuandoCriarTransacaoSemData() throws Exception {
		
		//Criação do objeto sem o atributo 'dataHora'
		Transacao transacao = new Transacao();
		transacao.setValor(2);
		
		Gson gson = new Gson();
		String transacaoJson = new String();
		
		Mockito
			.when(this.listaTransacaoService.add(transacao)).thenReturn(transacao);
		
		transacaoJson = "{\"valor\":" + gson.toJson(transacao.getValor()) + "}";
		
		//Requisição sem data esperando receber status http 400 - BadRequest
		mockMvc.perform(MockMvcRequestBuilders.post("/transacao")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(transacaoJson))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void deveRetornarBadRequest_QuandoCriarTransacaoDataNoFuturo() throws Exception {
		
		Transacao transacao = new Transacao();
		transacao.setValor(2);
		//Data no futuro
		transacao.setDataHora(OffsetDateTime.now().plusHours(5));
		
		Gson gson = new Gson();
		String transacaoJson = new String();
		
		Mockito
			.when(this.listaTransacaoService.add(transacao)).thenReturn(transacao);
		
		transacaoJson = "{\"valor\":" + gson.toJson(transacao.getValor()) + ",";
		transacaoJson += "\"dataHora\":" + gson.toJson(transacao.getDataHora().toString()) + "}";
		
		//Requisição com data no futuro esperando receber status http 400 - BadRequest
		mockMvc.perform(MockMvcRequestBuilders.post("/transacao")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(transacaoJson))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void deveRetornarBadRequest_QuandoCriarTransacaoValorNegativo() throws Exception {
		
		Transacao transacao = new Transacao();
		//Valor negativo
		transacao.setValor(-2);
		transacao.setDataHora(OffsetDateTime.now());
		
		Gson gson = new Gson();
		String transacaoJson = new String();
		
		Mockito
			.when(this.listaTransacaoService.add(transacao)).thenReturn(transacao);
		
		transacaoJson = "{\"valor\":" + gson.toJson(transacao.getValor()) + ",";
		transacaoJson += "\"dataHora\":" + gson.toJson(transacao.getDataHora().toString()) + "}";
		
		//Requisição com valor negativo esperando receber status http 400 - BadRequest
		mockMvc.perform(MockMvcRequestBuilders.post("/transacao")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(transacaoJson))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void deveRetornarNoContent_QuandoDeletarTransacoesSalvas() throws Exception {
		
		//Cria uma Lista de Transacao e a deixa vazia
		List<Transacao> listaTransacao = new ArrayList<>();
		listaTransacao.clear();
		
		Mockito
			.when(this.listaTransacaoService.clear()).thenReturn(listaTransacao);
		
		//Requisição esperando receber status http 204 - NoContent
		mockMvc.perform(MockMvcRequestBuilders.delete("/transacao")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

}
