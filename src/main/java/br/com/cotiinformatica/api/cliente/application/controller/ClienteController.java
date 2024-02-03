package br.com.cotiinformatica.api.cliente.application.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	
	@GetMapping
	public List<Cliente> findAll(){
		return clienteService.findAll();
	}
	
	@PutMapping("/{idCliente}")
	public ResponseEntity<Cliente> update(@RequestBody Cliente cliente, @PathVariable UUID idCliente){
		return clienteService.atualizar(cliente, idCliente);
	}
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@DeleteMapping("/{idCliente}")
	public Cliente delete(@PathVariable UUID idCliente) {
	return clienteService.excluir(idCliente);	
	}
	
}
