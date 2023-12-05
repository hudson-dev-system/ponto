package com.pontoeletronico.api.dto;

import java.util.Optional;

import jakarta.validation.constraints.NotEmpty;

public class LancamentoDTO {
	
	private Optional<Long> id = Optional.empty();
	private String data;
	private String localizacao;
	private String descricao;
	private String tipo;
	private Long funcionarioId;
	
	public LancamentoDTO() {
	}

	public Optional<Long> getId() {
		return id;
	}

	@NotEmpty(message = "O campo data não pode está vazio.")
	public String getData() {
		return data;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getTipo() {
		return tipo;
	}

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setId(Optional<Long> id) {
		this.id = id;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}
	
	
}
