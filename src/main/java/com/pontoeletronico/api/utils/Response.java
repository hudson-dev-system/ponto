package com.pontoeletronico.api.utils;

import java.util.ArrayList;
import java.util.List;

import com.pontoeletronico.api.enums.TipoLancamento;

public class Response<T> {
	
	private T data;
	private List<String> erros;
	private TipoLancamento[] lancamentos;
	
	public Response() {
	}
	
	public T getData() {
		return data;
	}
	public List<String> getErros() {
		if(this.erros == null ) {
			this.erros = new ArrayList<String>();
		}
		return erros;
	}
	public void setData(T data) {
		this.data = data;
	}
	public void setErros(List<String> erros) {
		this.erros = erros;
	}

	public TipoLancamento[] getLancamentos() {
		if(this.lancamentos == null) {
			this.lancamentos = null;
		}
		return lancamentos;
	}

	public void setLancamentos(TipoLancamento[] lancamentos) {
		this.lancamentos = lancamentos;
	}

	
}
