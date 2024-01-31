package br.com.cotiinformatica.api.cliente.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.api.cliente.domain.entities.Cliente;
import br.com.cotiinformatica.api.cliente.infrastructure.service.ClienteService;

@RestController
@RequestMapping("api/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@PostMapping
	public ResponseEntity<Cliente> create(@RequestBody Cliente cliente) {
		return clienteService.create(cliente); 
	}
}
