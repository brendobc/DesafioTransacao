package com.banco.digitau.model;

import java.time.OffsetDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;

public class Transacao {
	
	@PositiveOrZero(message = "Valor deve ser maior ou igual a zero")
	private double valor;
	
	@NotNull(message = "Este campo não pode ser nulo")
	@PastOrPresent(message = "A data deve ser atual ou estar no passado")
	private OffsetDateTime dataHora;

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public OffsetDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(OffsetDateTime dataHora) {
		this.dataHora = dataHora;
	}
	
	public void setDataHora(String dataHora) {
		this.dataHora = OffsetDateTime.parse(dataHora);
	}
}