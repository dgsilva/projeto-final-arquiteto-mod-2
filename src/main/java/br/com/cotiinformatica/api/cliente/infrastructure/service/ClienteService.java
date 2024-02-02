package br.com.cotiinformatica.api.cliente.infrastructure.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.cotiinformatica.api.cliente.domain.collections.LogClientes;
import br.com.cotiinformatica.api.cliente.domain.entities.Cliente;
import br.com.cotiinformatica.api.cliente.domain.entities.Endereco;
import br.com.cotiinformatica.api.cliente.infrastructure.repositories.ClienteMongoRepository;
import br.com.cotiinformatica.api.cliente.infrastructure.repositories.ClienteRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ClienteMongoRepository clienteMongoRepository;

	public ResponseEntity<Cliente> create(Cliente cliente) {
		
		if(clienteRepository.findByEmail(cliente.getEmail()).isPresent()) {
			log.info("Email, já está cadatrado, tente outro");
			throw new IllegalArgumentException("Email, já está cadatrado, tente outro");
		}
		
		if(clienteRepository.findByCpf(cliente.getCpf()).isPresent()) {
			log.info("CPF, já está cadatrado, tente outro");
			throw new IllegalArgumentException("CPF, já está cadatrado, tente outro");
		}
		
		try {
			cliente.setIdCliente(UUID.randomUUID());

			for (Endereco endereco : cliente.getEnderecos()) {
				endereco.setIdEndereco(UUID.randomUUID());
				endereco.setCliente(cliente);
			}
			clienteRepository.save(cliente);
			
			
			LogClientes logClientes = new LogClientes();
			logClientes.setId(UUID.randomUUID());
			logClientes.setDataHora(Instant.now());
			logClientes.setOperacao("CADASTRO");
			logClientes.setDescriao("Cliente cadastrado: " + cliente.getNome());
			clienteMongoRepository.save(logClientes);
			
			log.info("Os dados de cliente foram cadastrado com sucesso!!!");
			return ResponseEntity.status(HttpStatus.CREATED).body(cliente);

		} catch (Exception e) {

			log.error("Erro ao cadastrar o cliente" + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cliente);
		}
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public ResponseEntity<Cliente> atualizar(Cliente cliente, UUID idCliente) {
		try {

			if (!clienteRepository.existsById(idCliente)) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			// Obtenha o cliente existente do banco de dados
			Cliente clienteExistente = clienteRepository.findById(idCliente).orElse(null);

			cliente.setIdCliente(idCliente);
			for (Endereco endereco : cliente.getEnderecos()) {
				endereco.setCliente(clienteExistente);
			}
			clienteRepository.save(cliente);
			log.info("Cliente alterado com sucesso");
			return ResponseEntity.status(HttpStatus.OK).body(cliente);

		} catch (Exception e) {
			log.error("Erro ao realizar alteração do cliente");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}


	public Cliente excluir(UUID idCliente) {
		Optional<Cliente> clienteExcluir = clienteRepository.findById(idCliente);

		if (clienteExcluir.isPresent()) {
			clienteRepository.delete(clienteExcluir.get());
			log.info("Cliente excluido com sucesso");
		} else {
			log.error("Cliente não encontrado");
			extractedMessage();
		}

		return clienteExcluir.get();

	}
	
	private void extractedMessage() {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
	}
}
