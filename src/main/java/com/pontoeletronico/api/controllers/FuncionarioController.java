package com.pontoeletronico.api.controllers;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pontoeletronico.api.dto.FuncionarioDTO;
import com.pontoeletronico.api.entitys.Funcionario;
import com.pontoeletronico.api.intefaces.FuncionarioService;
import com.pontoeletronico.api.utils.PasswordUtil;
import com.pontoeletronico.api.utils.Response;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class FuncionarioController {
	
	private static final Logger log = LoggerFactory.getLogger(FuncionarioController.class);
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	public FuncionarioController() {
	}

	@PutMapping("/atualizar-funcionario/{id}")
	public ResponseEntity<Response<FuncionarioDTO>> atualizar(@Valid @RequestBody FuncionarioDTO funcionarioDTO,
			@PathVariable("id") Long id,
			BindingResult bindingResult) throws NoSuchAlgorithmException{
		
		log.info("ATUALIZANDO FUNCIONARIO DE ID {}: " + id);
		Response<FuncionarioDTO> response = new Response<FuncionarioDTO>();
		
		Optional<Funcionario> funcionario = this.funcionarioService.buscarId(id);
		
		if(!funcionario.isPresent()) {
			bindingResult.addError(new ObjectError("funcionario", "Funcionario não localizado."));
		}
		
		this.validarDados(funcionarioDTO, funcionario.get(), bindingResult);
		
		if(bindingResult.hasErrors()) {
			log.info("ERRO AO ATUALIZAR FUNCIONARIO.");
			bindingResult.getAllErrors().forEach(erro -> response.getErros().add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		this.funcionarioService.persistir(funcionario.get());
		response.setData(this.converteFuncionarioDTO(funcionario.get()));
		
		return ResponseEntity.ok(response);
	}

	

	private FuncionarioDTO converteFuncionarioDTO(Funcionario funcionario) {
		FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
		funcionarioDTO.setId(funcionario.getId());
		funcionarioDTO.setNome(funcionario.getNome());
		funcionarioDTO.setEmail(funcionario.getEmail());
		funcionario.getValorHoraOpt().ifPresent(
				valorHora -> funcionarioDTO.setValorHora(Optional.of(valorHora.toString())));
		funcionario.getQntHoraAlmocoOpt().ifPresent(
				qntHoraAlmoco -> funcionarioDTO.setQntHoraAlmoco(Optional.of(Float.toString(qntHoraAlmoco))));
		funcionario.getQntHoraTrabalhadaOpt().ifPresent(
				qntHoraTrabalhada -> funcionarioDTO.setQntHoraTrabalhada(Optional.of(Float.toString(qntHoraTrabalhada))));
		
		return funcionarioDTO;
	}

	private void validarDados(FuncionarioDTO funcionarioDTO, Funcionario funcionario,
			BindingResult bindingResult) throws NoSuchAlgorithmException{
		
		funcionario.setNome(funcionarioDTO.getNome());
		if(!funcionarioDTO.getEmail().equals(funcionario.getEmail())) {
			
			this.funcionarioService.buscarEmail(funcionarioDTO.getEmail()).ifPresent(
					result -> bindingResult.addError(new ObjectError("email", "Email já existente.")));
			
			funcionario.setEmail(funcionarioDTO.getEmail());
		}
		
		funcionario.setValorHora(null);
		funcionario.setQntHoraAlmoco(null);
		funcionario.setQntHoraTrabalhada(null);
		
		funcionarioDTO.getValorHora().ifPresent(
				valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));
		funcionarioDTO.getQntHoraAlmoco().ifPresent(
				qntHoraAlmoco -> funcionario.setQntHoraAlmoco(Float.valueOf(qntHoraAlmoco)));
		funcionarioDTO.getQntHoraTrabalhada().ifPresent(
				qntHoraTrabalhada -> funcionario.setQntHoraTrabalhada(Float.valueOf(qntHoraTrabalhada)));
		
		if(funcionarioDTO.getSenha().isPresent()) {
			funcionario.setSenha(PasswordUtil.gerarSenha(funcionarioDTO.getSenha().get()));
		}
	}
	
	
}
