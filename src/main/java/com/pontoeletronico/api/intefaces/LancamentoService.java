package com.pontoeletronico.api.intefaces;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.pontoeletronico.api.entitys.Lancamento;

public interface LancamentoService {
	
	Lancamento persistir(Lancamento lancamento);
	
	Optional<Lancamento> buscarId(Long id);
	
	void remover(Long id);
	
	Page<Lancamento> buscarFuncionarioId(Long funcionarioId, PageRequest pageRequest);

}
