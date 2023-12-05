package com.pontoeletronico.api.dto;

import java.util.Optional;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class CadastroPfDTO {
	
	private Long id;
	private String nome;
	private String cpf;
	private String email;
	private String senha;
	private Optional<String> valorHora = Optional.empty();
	private Optional<String> qntHoraTrabalhada = Optional.empty();
	private Optional<String> qntHoraAlmoco = Optional.empty();
	private String cnpj;
	
	public CadastroPfDTO() {
	}
	
	public Long getId() {
		return id;
	}

	@NotEmpty(message = "O campo nome nao pode ser vazio.")
	@Length(min = 2, max = 250, message = "O Nome deve ter entre 2 e 250 caracteres.")
	public String getNome() {
		return nome;
	}

	@NotEmpty(message = "O campo cpf nao pode estar vazio.")
	@CPF(message = "CPF invalido.")
	public String getCpf() {
		return cpf;
	}

	@NotEmpty(message = "O campo email nao pode estar vazio.")
	@Email(message = "Email invalido.")
	@Length(min = 4, max = 200, message = "O email deve ter entre 4 e 200 cracter.")
	public String getEmail() {
		return email;
	}

	@NotEmpty(message = "Senha nao pode ser vazia.")
	public String getSenha() {
		return senha;
	}
	
	@NotEmpty(message = "O campo cnpj nao pode estar vazio.")
	@CNPJ(message = "cnpj invalido.")
	public String getCnpj() {
		return cnpj;
	}

	public Optional<String> getValorHora() {
		return valorHora;
	}

	public Optional<String> getQntHoraTrabalhada() {
		return qntHoraTrabalhada;
	}

	public Optional<String> getQntHoraAlmoco() {
		return qntHoraAlmoco;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setValorHora(Optional<String> valorHora) {
		this.valorHora = valorHora;
	}

	public void setQntHoraTrabalhada(Optional<String> qntHoraTrabalhada) {
		this.qntHoraTrabalhada = qntHoraTrabalhada;
	}

	public void setQntHoraAlmoco(Optional<String> qntHoraAlmoco) {
		this.qntHoraAlmoco = qntHoraAlmoco;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
}
