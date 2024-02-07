package br.com.cotiinformatica.api.cliente.infrastructure.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.cotiinformatica.api.cliente.domain.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
	
	Optional<Cliente>findByEmail(String email);
	
	Optional<Cliente>findByCpf(String cpf);
	
	@Query("from Cliente c where c.idCliente = :idCliente")
	Optional<Cliente> findById(@Param("idCliente") UUID idCliente);
	
	@Query("from Cliente c where c.nome = :nome")
	Optional<Cliente> findByNome(@Param("nome") String nome);
	
	List<Cliente>findByNomeContaining(String nome);

}
