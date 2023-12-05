package com.pontoeletronico.api.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pontoeletronico.api.dto.EmpresaDTO;
import com.pontoeletronico.api.entitys.Empresa;
import com.pontoeletronico.api.intefaces.EmpresaService;
import com.pontoeletronico.api.utils.Response;

@RestController
@RequestMapping(value = "/api")
public class EmpresaController {
	
	private static final Logger log = LoggerFactory.getLogger(EmpresaController.class);
	
	@Autowired
	private EmpresaService empresaService;

	public EmpresaController() {
	}
	
	@GetMapping(value = "/consulta-cnpj/{cnpj}")
	public ResponseEntity<Response<EmpresaDTO>> buscaCnpj(@PathVariable("cnpj") String cnpj){
		log.info("BUSCANDO INFORMACOES PARA O CNPJ {} ", cnpj);
		
		Response<EmpresaDTO> response = new Response<EmpresaDTO>();
		
		Optional<Empresa> empresa = empresaService.buscarCnpj(cnpj);
		
		if(!empresa.isPresent()) {
			log.info("EMPRESA NAO LOCALIZADA PARA O CNPJ {} ", cnpj);
			response.getErros().add("Empresa nao localizada para o cnpj " + cnpj);
					
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(this.converterDTO(empresa.get()));
		return ResponseEntity.ok(response);
	}

	private EmpresaDTO converterDTO(Empresa empresa) {
		EmpresaDTO dto = new EmpresaDTO();
		
		dto.setId(empresa.getId());
		dto.setCnpj(empresa.getCnpj());
		dto.setRazaoSocial(empresa.getRazaoSocial());
		return dto;
	}
	
}
