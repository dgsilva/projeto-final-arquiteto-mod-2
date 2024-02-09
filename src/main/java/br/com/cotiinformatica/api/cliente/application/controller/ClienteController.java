package br.com.cotiinformatica.api.cliente.application.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("api/clientes")
@Tag(name = "Enpoint de Cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@Operation(summary = "Realizar o cadastro do cliente", method = "POST")
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping
	public ResponseEntity<Cliente> create(@Valid @RequestBody ClienteRequestDTO dto) {
		return clienteService.create(dto); 
	}
	@Operation(summary = "Realizar a consulta do cliente", method = "GET")
	@GetMapping
	public List<Cliente> findAll(){
		return clienteService.findAll();
	}
	@Operation(summary = "Realizar edição em massa do cliente", method = "PUT")
	@ResponseStatus(code = HttpStatus.OK)
	@PutMapping("/{idCliente}")
	public ResponseEntity<Cliente> update(@Valid @RequestBody ClienteRequestDTO dto, @PathVariable UUID idCliente){
		return clienteService.atualizar(dto, idCliente);
	}
	@Operation(summary = "Realizar edição parcial do cliente", method = "PUT")
	@PatchMapping("/{idCliente}")
	public ResponseEntity<Cliente>atualizarParcial(@PathVariable UUID idCliente, @RequestBody Map<String,Object>campos){
		return clienteService.atualizarParcial(idCliente, campos);
	}
	@Operation(summary = "Realizar Exclusão do cliente", method = "DEL")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@DeleteMapping("/{idCliente}")
	public Cliente delete(@PathVariable UUID idCliente) {
		return clienteService.excluir(idCliente);
	}
	@Operation(summary = "Realizar consulta pelo ID do cliente", method = "GET")
	@GetMapping("/{idCliente}")
	public ResponseEntity<Cliente> findById(@PathVariable UUID idCliente) { 
		return clienteService.findById(idCliente);
	}
	@Operation(summary = "Realizar consulta pelo nome do cliente", method = "GET")
	@GetMapping("/name{nome}")
	public ResponseEntity<Cliente> findByNome(@PathParam("nome") String nome) { 
		return clienteService.findByNome(nome);
	}
	@Operation(summary = "Realizar consulta pelo nome usando like do cliente", method = "GET")
	@GetMapping("/por-nome")
	public List<Cliente> findByNomeLike(String nome) { 
		return clienteService.findByNomeContaining(nome);
	}
	@Operation(summary = "Gerar relatorio do cliente", method = "GET")
	@GetMapping("gerar-relatorio")
	public ResponseEntity<byte[]> getRelatorio() throws Exception {
	    MultiValueMap<String, String> headersMap = new LinkedMultiValueMap<>();
	    headersMap.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
	    headersMap.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio.pdf");
	    HttpHeaders headers = new HttpHeaders(headersMap);
	    
	    return ResponseEntity.ok().headers(headers).body(clienteService.getReport());
	}
	
}
