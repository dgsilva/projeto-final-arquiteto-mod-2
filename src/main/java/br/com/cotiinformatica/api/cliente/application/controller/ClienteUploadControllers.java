package br.com.cotiinformatica.api.cliente.application.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.cotiinformatica.api.cliente.application.dto.response.ClienteResponseDTO;
import br.com.cotiinformatica.api.cliente.infrastructure.service.ClienteService;

@RestController
@RequestMapping("api/clientes/foto")
public class ClienteUploadControllers {

	@Autowired
	private ClienteService clienteService;
	
	
	@PostMapping(consumes = { "multipart/form-data" })
	public ResponseEntity<ClienteResponseDTO> uploadFotos(@RequestParam("idCliente") UUID idCliente, @RequestParam("imagem") MultipartFile imagem ){
		return clienteService.uploadFotos(idCliente, imagem);
	}
}
