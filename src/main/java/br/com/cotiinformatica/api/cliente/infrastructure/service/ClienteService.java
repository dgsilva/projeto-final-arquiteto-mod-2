package br.com.cotiinformatica.api.cliente.infrastructure.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.api.cliente.domain.entities.Cliente;
import br.com.cotiinformatica.api.cliente.domain.entities.Endereco;
import br.com.cotiinformatica.api.cliente.infrastructure.repositories.ClienteRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	
	public ResponseEntity<Cliente> create(Cliente cliente) {
		try {
			cliente.setIdCliente(UUID.randomUUID());

			for (Endereco endereco : cliente.getEnderecos()) {
				endereco.setIdEndereco(UUID.randomUUID());
				endereco.setCliente(cliente);
			}
			clienteRepository.save(cliente);
			log.info("Os dados de cliente foram cadastrado com sucesso!!!");
			return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
			
		} catch (Exception e) {
			
			log.error("Erro ao cadastrar o cliente" + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cliente);
		}
	}
}
