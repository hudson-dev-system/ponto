package com.pontoeletronico.api.controllers;

import java.security.NoSuchAlgorithmException;

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

import com.pontoeletronico.api.dto.CadastroPjDTO;
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
public class CadastroPjController {

	private static final Logger log = LoggerFactory.getLogger(CadastroPjController.class);
	
	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	public CadastroPjController() {
		
	}
	
	@PostMapping(value = "/cadastro-pj")
	public ResponseEntity<Response<CadastroPjDTO>> cadastro(@RequestBody @Valid CadastroPjDTO cadastroPjDTO, 
			BindingResult bindingResult) throws NoSuchAlgorithmException{
		
		log.info("CADASTRANDO PESSOA JURIDICA. {} ", cadastroPjDTO.toString());
		Response<CadastroPjDTO> response = new Response<CadastroPjDTO>();
		
		validarDados(cadastroPjDTO, bindingResult);
		
		Empresa empresa = this.converterEmpresa(cadastroPjDTO);
		
		Funcionario funcionario = this.converterFuncionario(cadastroPjDTO, bindingResult);
		
		if(bindingResult.hasErrors()) {
			log.error("ERRO VALIDANDO DADOS PJ: {} ", bindingResult.getAllErrors());
			bindingResult.getAllErrors().forEach(erro -> response.getErros().add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		this.empresaService.persistir(empresa);
		funcionario.setEmpresa(empresa);
		this.funcionarioService.persistir(funcionario);
		
		response.setData(this.converterCadastroDTO(funcionario));
		
		return ResponseEntity.ok(response);
		
	}

	private CadastroPjDTO converterCadastroDTO(Funcionario funcionario) {
		
		CadastroPjDTO cadastroPjDTO = new CadastroPjDTO();
		cadastroPjDTO.setId(funcionario.getId());
		cadastroPjDTO.setNome(funcionario.getNome());
		cadastroPjDTO.setCpf(funcionario.getCpf());
		cadastroPjDTO.setEmail(funcionario.getEmail());
		cadastroPjDTO.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
		cadastroPjDTO.setCnpj(funcionario.getEmpresa().getCnpj());
		
		return cadastroPjDTO;
	}

	private Funcionario converterFuncionario(@Valid CadastroPjDTO cadastroPjDTO, BindingResult bindingResult) 
		throws NoSuchAlgorithmException{
		Funcionario funcionario = new Funcionario();
		
		funcionario.setCpf(cadastroPjDTO.getCpf());
		funcionario.setNome(cadastroPjDTO.getNome());
		funcionario.setEmail(cadastroPjDTO.getEmail());
		funcionario.setPerfil(Perfil.ROLE_ADMIN);
		funcionario.setSenha(PasswordUtil.gerarSenha(cadastroPjDTO.getSenha()));
		
		return funcionario;
	}

	private Empresa converterEmpresa(@Valid CadastroPjDTO cadastroPjDTO) {
		Empresa empresa = new Empresa();
		
		empresa.setCnpj(cadastroPjDTO.getCnpj());
		empresa.setRazaoSocial(cadastroPjDTO.getRazaoSocial());
		return null;
	}

	private void validarDados(@Valid CadastroPjDTO cadastroPjDTO, BindingResult bindingResult) {
		this.empresaService.buscarCnpj(cadastroPjDTO.getCnpj())
			.ifPresent(emp -> bindingResult.addError(new ObjectError("empresa", "CNPJ já existe na base de dados.")));
		
		this.funcionarioService.buscarCpf(cadastroPjDTO.getCpf())
			.ifPresent(func -> bindingResult.addError(new ObjectError("func", "CPF já existe na base de dados.")));
		
		this.funcionarioService.buscarEmail(cadastroPjDTO.getEmail())
			.ifPresent(func -> bindingResult.addError(new ObjectError("func", "EMAIL ja existe na base de dados.")));
		
	}
	
}
