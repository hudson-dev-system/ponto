package com.pontoeletronico.api.intefaces;

import java.util.Optional;

import com.pontoeletronico.api.entitys.Funcionario;

public interface FuncionarioService {
	
	Optional<Funcionario> buscarEmail(String email);
	
	Optional<Funcionario> buscarCpf(String cpf);
	
	Optional<Funcionario> buscarId(Long id);
	
	Optional<Funcionario> buscarCpfOuEmail(String cpf, String email);
	
	Funcionario persistir(Funcionario funcionario);

}
