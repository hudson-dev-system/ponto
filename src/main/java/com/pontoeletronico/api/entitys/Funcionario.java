package com.pontoeletronico.api.entitys;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.pontoeletronico.api.enums.Perfil;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "funcionario")
public class Funcionario implements Serializable{

	private static final long serialVersionUID = -6594620801780567368L;
	
	private Long id;
	private String nome;
	private String cpf;
	private String email;
	private String senha;
	private Date dataCadastro;
	private Date dataAtualizacao;
	private List<Lancamento> lancamentos;
	private Empresa empresa;
	private Perfil perfil;
	private BigDecimal valorHora;
	private Float qntHoraAlmoco;
	private Float qntHoraTrabalhada;

	public Funcionario() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public Empresa getEmpresa() {
		return empresa;
	}
	
	@Column(name = "nome", nullable = false)
	public String getNome() {
		return nome;
	}

	@Column(name = "cpf", nullable = false)
	public String getCpf() {
		return cpf;
	}

	@Column(name = "email", nullable = false)
	public String getEmail() {
		return email;
	}

	@Column(name = "senha", nullable = false)
	public String getSenha() {
		return senha;
	}

	@Column(name = "data_cadastro", nullable = false)
	public Date getDataCadastro() {
		return dataCadastro;
	}

	@Column(name = "data_atualizacao", nullable = false)
	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	@OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	@Column(name = "perfil", nullable = false)
	@Enumerated(EnumType.STRING)
	public Perfil getPerfil() {
		return perfil;
	}

	@Column(name = "valor_hora", nullable = true)
	public BigDecimal getValorHora() {
		return valorHora;
	}

	@Column(name = "qtd_horas_almoco", nullable = true)
	public Float getQntHoraAlmoco() {
		return qntHoraAlmoco;
	}

	@Column(name = "qtd_horas_trabalhadas_dia", nullable = true)
	public Float getQntHoraTrabalhada() {
		return qntHoraTrabalhada;
	}
	
	@Transient
	public Optional<BigDecimal> getValorHoraOpt(){
		return Optional.ofNullable(valorHora);
	}
	
	@Transient
	public Optional<Float> getQntHoraAlmocoOpt(){
		return Optional.ofNullable(qntHoraAlmoco);
	}
	
	@Transient
	public Optional<Float> getQntHoraTrabalhadaOpt(){
		return Optional.ofNullable(qntHoraTrabalhada);
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

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public void setValorHora(BigDecimal valorHora) {
		this.valorHora = valorHora;
	}

	public void setQntHoraAlmoco(Float qntHoraAlmoco) {
		this.qntHoraAlmoco = qntHoraAlmoco;
	}

	public void setQntHoraTrabalhada(Float qntHoraTrabalhada) {
		this.qntHoraTrabalhada = qntHoraTrabalhada;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@PreUpdate
	public void preUpdate() {
		this.dataAtualizacao = new Date();
	}
	
	@PrePersist
	public void prePersist() {
		final Date agora = new Date();
		this.dataCadastro = agora;
		this.dataAtualizacao = agora;
	}

	@Override
	public String toString() {
		return "Funcionario [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", email=" + email + ", senha=" + senha
				+ ", dataCadastro=" + dataCadastro + ", dataAtualizacao=" + dataAtualizacao + ", lancamentos="
				+ lancamentos + ", empresa=" + empresa + ", perfil=" + perfil + ", valorHora=" + valorHora
				+ ", qntHoraAlmoco=" + qntHoraAlmoco + ", qntHoraTrabalhada=" + qntHoraTrabalhada + "]";
	}
	
}
