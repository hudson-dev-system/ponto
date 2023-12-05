package com.pontoeletronico.api.dto;

import java.util.Optional;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class FuncionarioDTO {

	private Long id;
	private String nome;
	private String email;
	private Optional<String> senha = Optional.empty();
	private Optional<String> valorHora = Optional.empty();
	private Optional<String> qntHoraAlmoco = Optional.empty();
	private Optional<String> qntHoraTrabalhada = Optional.empty();
	
	public FuncionarioDTO() {
	}
	
	public Long getId() {
		return id;
	}
	
	@NotEmpty(message = "O campo NOME nao pode ser vazio.")
	@Length(min = 2, max = 250, message = "O Nome deve ter entre 2 e 250 caracteres.")
	public String getNome() {
		return nome;
	}
	public Optional<String> getSenha() {
		return senha;
	}
	
	@Email
	@NotEmpty(message = "O campo EMAIL nao pode ser vazio.")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Optional<String> getValorHora() {
		return valorHora;
	}
	public Optional<String> getQntHoraAlmoco() {
		return qntHoraAlmoco;
	}
	public Optional<String> getQntHoraTrabalhada() {
		return qntHoraTrabalhada;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setSenha(Optional<String> senha) {
		this.senha = senha;
	}
	public void setValorHora(Optional<String> valorHora) {
		this.valorHora = valorHora;
	}
	public void setQntHoraAlmoco(Optional<String> qntHoraAlmoco) {
		this.qntHoraAlmoco = qntHoraAlmoco;
	}
	public void setQntHoraTrabalhada(Optional<String> qntHoraTrabalhada) {
		this.qntHoraTrabalhada = qntHoraTrabalhada;
	}
	
	
}
