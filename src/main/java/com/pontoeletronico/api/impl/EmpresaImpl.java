package com.pontoeletronico.api.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pontoeletronico.api.entitys.Empresa;
import com.pontoeletronico.api.intefaces.EmpresaService;
import com.pontoeletronico.api.repositorys.EmpresaRepostirory;

@Service
public class EmpresaImpl implements EmpresaService{
	
	private static final Logger log = LoggerFactory.getLogger(EmpresaImpl.class);
	
	@Autowired 
	private EmpresaRepostirory empresaRepostirory;

	@Override
	public Optional<Empresa> buscarCnpj(String cnpj) {
		log.info("BUSCANDO INFORMACOES DE CNPJ {} ", cnpj);
		return Optional.ofNullable(empresaRepostirory.findByCnpj(cnpj));
	}

	@Override
	public Empresa persistir(Empresa empresa) {
		log.info("SALVANDO EMPRESA NO DB {} ", empresa.toString());
		return this.empresaRepostirory.save(empresa);
	}

}
