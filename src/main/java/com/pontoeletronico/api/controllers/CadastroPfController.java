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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pontoeletronico.api.dto.CadastroPfDTO;
import com.pontoeletronico.api.entitys.Empresa;
import com.pontoeletronico.api.entitys.Funcionario;
import com.pontoeletronico.api.enums.Perfil;
import com.pontoeletronico.api.intefaces.EmpresaService;
import com.pontoeletronico.api.intefaces.FuncionarioService;
import com.pontoeletronico.api.utils.PasswordUtil;
import com.pontoeletronico.api.utils.Response;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api")
public class CadastroPfController {
	
	private static final Logger log = LoggerFactory.getLogger(CadastroPfController.class);

	@Autowired
	private FuncionarioService funcionarioService;
	
	@Autowired
	private EmpresaService empresaService;
	
	public CadastroPfController () {
		
	}
	
	@PostMapping(value = "/cadastro-pf")
	public ResponseEntity<Response<CadastroPfDTO>> cadastro(@Valid @RequestBody CadastroPfDTO cadastroFuncionarioDTO,
			BindingResult bindingResult) throws NoSuchAlgorithmException{
			
		log.info("CADASTRANDO FUNCIONARIO:");
		Response<CadastroPfDTO> response = new Response<CadastroPfDTO>();
		
		validarDados(cadastroFuncionarioDTO, bindingResult);
		
		Funcionario funcionario = this.converterDTOFuncionario(cadastroFuncionarioDTO, bindingResult);
		
		if(bindingResult.hasErrors()) {
			log.info("ERRO AO CADASTRAR FUNCIONARIO!!");
			bindingResult.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		Optional<Empresa> empresa = this.empresaService.buscarCnpj(cadastroFuncionarioDTO.getCnpj());
		
		empresa.ifPresent(emp -> funcionario.setEmpresa(emp));
		this.funcionarioService.persistir(funcionario);
		
		response.setData(converteFuncionarioDTO(funcionario));
		
		
		return ResponseEntity.ok().body(response);
		
	}

	
	public CadastroPfDTO converteFuncionarioDTO(Funcionario funcionario) {
		
		CadastroPfDTO cadastroFuncionarioDTO = new CadastroPfDTO();
		cadastroFuncionarioDTO.setId(funcionario.getId());
		cadastroFuncionarioDTO.setNome(funcionario.getNome());
		cadastroFuncionarioDTO.setCpf(funcionario.getCpf());
		cadastroFuncionarioDTO.setEmail(funcionario.getEmail());
		cadastroFuncionarioDTO.setCnpj(funcionario.getEmpresa().getCnpj());
		funcionario.getValorHoraOpt().ifPresent(
				valorHora -> cadastroFuncionarioDTO.setValorHora(Optional.of(valorHora.toString())));
		funcionario.getQntHoraAlmocoOpt().ifPresent(
				qntHoraAlmoco -> cadastroFuncionarioDTO.setQntHoraAlmoco(Optional.of(Float.toString(qntHoraAlmoco))));
		funcionario.getQntHoraTrabalhadaOpt().ifPresent(
				qntHoraTrabalhada -> cadastroFuncionarioDTO.setQntHoraTrabalhada(Optional.of(Float.toString(qntHoraTrabalhada))));
		
		return cadastroFuncionarioDTO;
	}

	private Funcionario converterDTOFuncionario(@Valid CadastroPfDTO cadastroFuncionarioDTO,
			BindingResult bindingResult) {
			
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(cadastroFuncionarioDTO.getNome());
		funcionario.setCpf(cadastroFuncionarioDTO.getCpf());
		funcionario.setEmail(cadastroFuncionarioDTO.getEmail());
		funcionario.setPerfil(Perfil.ROLE_USUARIO);
		funcionario.setSenha(PasswordUtil.gerarSenha(cadastroFuncionarioDTO.getSenha()));
		cadastroFuncionarioDTO.getValorHora().ifPresent(
				valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));
		cadastroFuncionarioDTO.getQntHoraTrabalhada().ifPresent(
				qntHoraTrabalhada -> funcionario.setQntHoraTrabalhada(Float.valueOf(qntHoraTrabalhada)));
		cadastroFuncionarioDTO.getQntHoraAlmoco().ifPresent(
				qntHoraAlmoco -> funcionario.setQntHoraAlmoco(Float.valueOf(qntHoraAlmoco)));
		
		return funcionario;
	}

	private void validarDados(CadastroPfDTO cadastroFuncionarioDTO, BindingResult bindingResult) {
		Optional<Empresa> empresa = this.empresaService.buscarCnpj(cadastroFuncionarioDTO.getCnpj());
		if(!empresa.isPresent()) {
			bindingResult.addError(new ObjectError("empresa", "Empresa não localizada!"));
		}
		
		this.funcionarioService.buscarCpf(cadastroFuncionarioDTO.getCpf()).ifPresent(
				res -> bindingResult.addError(new ObjectError("funcionarioCpf", "CPF já existe!")));
		
		this.funcionarioService.buscarEmail(cadastroFuncionarioDTO.getEmail()).ifPresent(
				res -> bindingResult.addError(new ObjectError("funcionarioEmail", "EMAIL já existe!")));
		
	}
}
