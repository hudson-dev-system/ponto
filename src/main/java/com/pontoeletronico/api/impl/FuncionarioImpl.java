package com.pontoeletronico.api.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pontoeletronico.api.entitys.Funcionario;
import com.pontoeletronico.api.intefaces.FuncionarioService;
import com.pontoeletronico.api.repositorys.FuncionarioRepository;

@Service
public class FuncionarioImpl implements FuncionarioService{
	
	private static final Logger log = LoggerFactory.getLogger(FuncionarioImpl.class);
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Override
	public Optional<Funcionario> buscarEmail(String email) {
		log.info("BUSCANDO FUNCIONARIO POR EMAIL {} ", email);
		return Optional.ofNullable(this.funcionarioRepository.findByEmail(email));
	}

	@Override
	public Optional<Funcionario> buscarCpf(String cpf) {
		log.info("BUSCANDO FUNCIONARIO PELO CPF {} ", cpf);
		return Optional.ofNullable(this.funcionarioRepository.findByCpf(cpf));
	}

	@Override
	public Optional<Funcionario> buscarId(Long id) {
		log.info("BUSCANDO FUNCIONARIO PELO ID {} ", id);
		return Optional.ofNullable(this.funcionarioRepository.findById(id).get());
	}

	@Override
	public Funcionario persistir(Funcionario funcionario) {
		log.info("SALVANDO FUNCIONARIO NO DB DE ID {} ", funcionario.getId());
		return this.funcionarioRepository.save(funcionario);
	}

	@Override
	public Optional<Funcionario> buscarCpfOuEmail(String cpf, String email) {
		log.info("BUSCANDO FUNCIONARIO POR CPF OU EMAIL {} {} ", cpf, email );
		return Optional.ofNullable(this.funcionarioRepository.findByCpfOrEmail(cpf, email));
	}

}
