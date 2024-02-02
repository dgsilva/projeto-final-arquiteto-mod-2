package br.com.cotiinformatica.api.cliente.infrastructure.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cotiinformatica.api.cliente.domain.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
	
	Optional<Cliente>findByEmail(String email);
	
	Optional<Cliente>findByCpf(String cpf);
	
}
