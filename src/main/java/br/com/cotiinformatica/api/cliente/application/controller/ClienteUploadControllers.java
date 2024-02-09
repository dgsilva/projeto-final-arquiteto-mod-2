package br.com.cotiinformatica.api.cliente.application.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.cotiinformatica.api.cliente.application.dto.response.ClienteResponseDTO;
import br.com.cotiinformatica.api.cliente.infrastructure.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/clientes/foto")
@Tag(name = "Enpoint de Upload")
public class ClienteUploadControllers {

	@Autowired
	private ClienteService clienteService;
	
	@Operation(summary = "Realizar o upload de foto do cliente", method = "POST")
	@PostMapping(consumes = { "multipart/form-data" })
	public ResponseEntity<ClienteResponseDTO> uploadFotos(@RequestParam("idCliente") UUID idCliente, @RequestParam("imagem") MultipartFile imagem ){
		return clienteService.uploadFotos(idCliente, imagem);
	}
	@Operation(summary = "Realizar o upload de edição da foto do cliente", method = "PUT")
	@PutMapping(value =  "/{idCliente}/atualizar-foto", consumes = "multipart/form-data")
	public ResponseEntity<ClienteResponseDTO> atualizarFotoCliente(@PathVariable UUID idCliente, @RequestParam("imagem") MultipartFile imagem){
		return clienteService.atualizarFotoCliente(idCliente, imagem);
	}
}
