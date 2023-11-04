package com.pontoeletronico.api.intefaces;

import java.util.Optional;

import com.pontoeletronico.api.entitys.Empresa;

public interface EmpresaService {
	
	Optional<Empresa> buscarCnpj(String cnpj);
	
	Empresa persistir(Empresa empresa);
}
