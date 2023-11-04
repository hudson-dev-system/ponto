package com.pontoeletronico.api.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.pontoeletronico.api.entitys.Lancamento;
import com.pontoeletronico.api.intefaces.LancamentoService;
import com.pontoeletronico.api.repositorys.LancamentoRepository;

@Service
public class LancamentoImpl implements LancamentoService{
	
	private static final Logger log = LoggerFactory.getLogger(LancamentoImpl.class);

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	public Lancamento persistir(Lancamento lancamento) {
		log.info("CADASTRANDO LANCAMENTO {} ", lancamento);
		return this.lancamentoRepository.save(lancamento);
	}

	public Optional<Lancamento> buscarId(Long id) {
		log.info("BUSCANDO LANCAMENTO DE ID {} ", id);
		return this.lancamentoRepository.findById(id);
	}

	@Override
	public void remover(Long id) {
		log.info("DELETANDO LANCAMENTO POR ID {} ", id);
		this.lancamentoRepository.deleteById(id);
	}

	@Override
	public Page<Lancamento> buscarFuncionarioId(Long funcionarioId, PageRequest pageRequest) {
		log.info("BUSCA PAGINADA DO LANCAMENTO DO FUNCIONARIO ID {} PAG {} ", funcionarioId, pageRequest);
		return this.lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest);
	}
	
	public List<Lancamento> buscarFuncionarioId(Long funcionarioId) {
		log.info("BUSCA LISTA DE LANCAMENTOS DO FUNCIONARIO DE ID {} ", funcionarioId);
		return this.lancamentoRepository.findByFuncionarioId(funcionarioId);
	}

}
