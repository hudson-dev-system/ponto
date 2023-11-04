package com.pontoeletronico.api.dto;

public class EmpresaDTO {
	
	private Long id;
	private String cnpj;
	private String razaoSocial;
	
	public EmpresaDTO() {
	}
	
	public Long getId() {
		return id;
	}
	public String getCnpj() {
		return cnpj;
	}
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	
	

}
