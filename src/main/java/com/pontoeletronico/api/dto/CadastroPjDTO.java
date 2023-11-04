package com.pontoeletronico.api.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class CadastroPjDTO {
	
	private Long id;
	private String nome;
	private String cpf;
	private String email;
	private String razaoSocial;
	private String cnpj;
	private String senha;
	
	public CadastroPjDTO() {
		
	}

	public Long getId() {
		return id;
	}

	@NotEmpty(message = "O campo nome não pode ser vazio.")
	@Length(min = 3, max = 200, message = "O campo nome deve ter entre 3 e 200 caracteres.")
	public String getNome() {
		return nome;
	}

	@NotEmpty(message = "O campo cpf não pode estar vazio.")
	@CPF(message = "Cpf invalido.")
	public String getCpf() {
		return cpf;
	}

	@NotEmpty(message = "O campo não pode ser vazio.")
	@Length(min = 5, max = 200, message = "O campo email deve ter entre 5 e 200 caracter.")
	@Email(message = "Email invalido.")
	public String getEmail() {
		return email;
	}

	@NotEmpty(message = "O campo nao pode ser vazio.")
	@Length(min = 3, max = 200, message = "O campo deve ter entre 5 e 200 caracter.")
	public String getRazaoSocial() {
		return razaoSocial;
	}

	@NotEmpty(message = "Campo noa pode ser vazio.")
	@CNPJ(message = "CNPJ invalido.")
	public String getCnpj() {
		return cnpj;
	}

	@NotEmpty(message = "Senha nao pode ser vazia.")
	public String getSenha() {
		return senha;
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

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	
}
