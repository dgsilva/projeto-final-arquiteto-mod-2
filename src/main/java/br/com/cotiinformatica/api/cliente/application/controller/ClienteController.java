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
import br.com.cotiinformatica.api.cliente.application.dto.request.ClienteRequestDTO;
import br.com.cotiinformatica.api.cliente.domain.entities.Cliente;
import br.com.cotiinformatica.api.cliente.infrastructure.service.ClienteService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("api/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping
	public ResponseEntity<Cliente> create(@Valid @RequestBody ClienteRequestDTO dto) {
		return clienteService.create(dto); 
	}
	
	@GetMapping
	public List<Cliente> findAll(){
		return clienteService.findAll();
	}
	@ResponseStatus(code = HttpStatus.OK)
	@PutMapping("/{idCliente}")
	public ResponseEntity<Cliente> update(@Valid @RequestBody ClienteRequestDTO dto, @PathVariable UUID idCliente){
		return clienteService.atualizar(dto, idCliente);
	}
	
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@DeleteMapping("/{idCliente}")
	public Cliente delete(@PathVariable UUID idCliente) {
		return clienteService.excluir(idCliente);
	}
	
	@GetMapping("/{idCliente}")
	public ResponseEntity<Cliente> findById(@PathVariable UUID idCliente) { 
		return clienteService.findById(idCliente);
	}
	
	@GetMapping("/name{nome}")
	public ResponseEntity<Cliente> findByNome(@PathParam("nome") String nome) { 
		return clienteService.findByNome(nome);
	}
	
	@GetMapping("/por-nome")
	public List<Cliente> findByNomeLike(String nome) { 
		return clienteService.findByNomeContaining(nome);
	}
}
