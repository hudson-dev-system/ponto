package com.pontoeletronico.api.entitys;

import java.io.Serializable;
import java.util.Date;

import com.pontoeletronico.api.enums.TipoLancamento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "lancamentos")
public class Lancamento implements Serializable{

	private static final long serialVersionUID = 2778709098292437467L;

	private Long id;
	private String descricao;
	private String localizacao;
	private Date data;
	private Date dataCadastro;
	private Date dataAtualizacao;
	private TipoLancamento tipoLancamento;
	private Funcionario funcionario;
	
	public Lancamento(){
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	public Funcionario getFuncionario() {
		return funcionario;
	}
	
	@Column(name = "descricao", nullable = true)
	public String getDescricao() {
		return descricao;
	}

	@Column(name = "localizacao", nullable = true)
	public String getLocalizacao() {
		return localizacao;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data", nullable = false)
	public Date getData() {
		return data;
	}

	@Column(name = "data_cadastro", nullable = false)
	public Date getDataCadastro() {
		return dataCadastro;
	}

	@Column(name = "data_atualizacao", nullable = false)
	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo", nullable = false)
	public TipoLancamento getTipoLancamento() {
		return tipoLancamento;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public void setTipoLancamento(TipoLancamento tipoLancamento) {
		this.tipoLancamento = tipoLancamento;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
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
		return "Lancamento [id=" + id + ", descricao=" + descricao + ", localizacao=" + localizacao + ", data=" + data
				+ ", dataCadastro=" + dataCadastro + ", dataAtualizacao=" + dataAtualizacao + ", tipoLancamento="
				+ tipoLancamento + ", funcionario=" + funcionario + "]";
	}
	
}
