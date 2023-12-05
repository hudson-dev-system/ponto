package com.pontoeletronico.api.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pontoeletronico.api.dto.LancamentoDTO;
import com.pontoeletronico.api.entitys.Funcionario;
import com.pontoeletronico.api.entitys.Lancamento;
import com.pontoeletronico.api.enums.TipoLancamento;
import com.pontoeletronico.api.intefaces.FuncionarioService;
import com.pontoeletronico.api.intefaces.LancamentoService;
import com.pontoeletronico.api.utils.Response;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api")
public class LancamentoController {

	private static final Logger log = LoggerFactory.getLogger(LancamentoController.class);
	
	private final SimpleDateFormat formatarData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Value("${paginacao.qtd_por_pagina}")
	private int qntPorPagina;
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	public LancamentoController() {
	}

	@PostMapping(value = "/cadastrar-lancamento")
	public ResponseEntity<Response<LancamentoDTO>> cadastrar(@Valid @RequestBody LancamentoDTO lancamentoDTO,
			BindingResult bindingResult) throws ParseException {
		
		log.info("CADASTRANDO LANCAMENTO: {} ", lancamentoDTO.toString());
		Response<LancamentoDTO> response = new Response<LancamentoDTO>();
		
		this.validarDados(lancamentoDTO, bindingResult);
		
		Lancamento lancamento = this.convertDTOLancamento(lancamentoDTO, bindingResult);
		
		if(bindingResult.hasErrors()) {
			log.info("ERRO AO CADASTRAR LANCAMENTO.");
			bindingResult.getAllErrors().forEach(erro -> response.getErros().add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		this.lancamentoService.persistir(lancamento);
		response.setData(this.convertLancamentoDTO(lancamento));
		
		return ResponseEntity.ok(response);
	}

	public LancamentoDTO convertLancamentoDTO(Lancamento lancamento) {
		LancamentoDTO dto = new LancamentoDTO();
		
		dto.setData(this.formatarData.format(lancamento.getData()));
		dto.setId(Optional.of(lancamento.getId()));
		dto.setTipo(lancamento.getTipoLancamento().toString());
		dto.setDescricao(lancamento.getDescricao());
		dto.setFuncionarioId(lancamento.getFuncionario().getId());
		dto.setLocalizacao(lancamento.getLocalizacao());
		
		return dto;
	}

	public Lancamento convertDTOLancamento(@Valid LancamentoDTO lancamentoDTO, BindingResult bindingResult) throws ParseException {
		
		Lancamento lancamento = new Lancamento();
		
		if(lancamentoDTO.getId().isPresent()) {
			Optional<Lancamento> lanc = this.lancamentoService.buscarId(lancamentoDTO.getId().get());
			if(lanc.isPresent()) {
				lancamento = lanc.get();
			} else {
				bindingResult.addError(new ObjectError("lancamento", "Lancamento não localizado."));
			}
		} else {
			lancamento.setFuncionario(new Funcionario());
			lancamento.getFuncionario().setId(lancamentoDTO.getFuncionarioId());
		}
		
		lancamento.setData(this.formatarData.parse(lancamentoDTO.getData()));
		lancamento.setDescricao(lancamentoDTO.getDescricao());
		lancamento.setLocalizacao(lancamentoDTO.getLocalizacao());
		
		if(EnumUtils.isValidEnum(TipoLancamento.class, lancamentoDTO.getTipo())) {
			lancamento.setTipoLancamento(TipoLancamento.valueOf(lancamentoDTO.getTipo()));
		} else {
			bindingResult.addError(new ObjectError("lancamento", "Tipo não identificado."));
		}
		
		return lancamento;
	}

	public void validarDados(@Valid LancamentoDTO lancamentoDTO, BindingResult bindingResult) {
		Optional<Funcionario> funcionario = this.funcionarioService.buscarId(lancamentoDTO.getFuncionarioId());
		
		if(funcionario == null) {
			bindingResult.addError(new ObjectError("funcionario", "Funcionario não existe."));
			return;
		}
		
		log.info("BUSCANDO FUNCIONARIO PELO ID: {} ", lancamentoDTO.getFuncionarioId());
		if(!funcionario.isPresent()) {
			bindingResult.addError(new ObjectError("funcionario", "Funcionario não localizado."));
		}
	}
	
	@DeleteMapping(value = "/delete-lancamento/{id}")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") Long id){
		log.info("DELETANDO LANCAMENTO DE ID {} " + id);
		Response<String> response = new Response<String>();
		
		Optional<Lancamento> optionalLancamento = this.lancamentoService.buscarId(id);
		
		if(!optionalLancamento.isPresent()) {
			log.info("LANCAMENTO NÃO LOCALIZADO PARA O ID {} " + id);
			response.getErros().add("Lancamento não localizado na base de dados para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}
		
		this.lancamentoService.remover(id);
		
		return ResponseEntity.ok(new Response<String>());
	}
	
	@GetMapping(value = "/buscar-lancamento/{id}")
	public ResponseEntity<Response<LancamentoDTO>> buscarLancamento(@PathVariable("id") Long id){
		log.info("BUSCANDO LANCAMENTO PELO ID " + id);
		Response<LancamentoDTO> response = new Response<LancamentoDTO>();
		
		Optional<Lancamento> lancamento = this.lancamentoService.buscarId(id);
		
		if(!lancamento.isPresent()) {
			log.info("LANCAMENTO NÃO LOCALIZADO PARA O ID {} " + id);
			response.getErros().add("Lancamento não localizado.");
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(this.convertLancamentoDTO(lancamento.get()));
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/lancamentos-por-funcionario/{id}")
	public ResponseEntity<Response<Page<LancamentoDTO>>> paginaLancamento(
			@PathVariable("id")Long idFuncionario,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "campoOrd", defaultValue = "id") String campoOrd,
			@RequestParam(value = "ord", defaultValue = "DESC") String ord){
		log.info("BUSCANDO PAGINA DE LANCAMENTO.");
		Response<Page<LancamentoDTO>> response = new Response<Page<LancamentoDTO>>();
		
		PageRequest pageRequest = PageRequest.of(pag, qntPorPagina, Direction.valueOf(ord), campoOrd);
		Page<Lancamento> pageLancamento = this.lancamentoService.buscarFuncionarioId(idFuncionario, pageRequest);
		Page<LancamentoDTO> pageDTO = pageLancamento.map(lancamento -> this.convertLancamentoDTO(lancamento));
		
		response.setData(pageDTO);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping(value = "/atualizar-lancamento/{id}")
	public ResponseEntity<Response<LancamentoDTO>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody LancamentoDTO dto,
			BindingResult bindingResult) throws ParseException{
		log.info("ATUALIZANDO LANCAMENTO DE ID " + id);
		Response<LancamentoDTO> response = new Response<LancamentoDTO>();
		
		this.validarDados(dto, bindingResult);
		dto.setId(Optional.of(id));
		Lancamento lancamento = this.convertDTOLancamento(dto, bindingResult);
		
		if(bindingResult.hasErrors()) {
			log.info("ERRO AO ATUALIZAR LANCAMENTO.");
			bindingResult.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		this.lancamentoService.persistir(lancamento);
		response.setData(this.convertLancamentoDTO(lancamento));
		
		return ResponseEntity.ok(response);
	}
}
