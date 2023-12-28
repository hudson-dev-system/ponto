package com.pontoeletronico.api.dto;

import com.pontoeletronico.api.enums.TipoLancamento;

public class LancamentosDTO {

	private TipoLancamento[] lancamentos;

	public TipoLancamento[] getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(TipoLancamento[] lancamentos) {
		this.lancamentos = lancamentos;
	}
}
