package br.com.cotiinformatica.api.cliente.infrastructure.repositories;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.cotiinformatica.api.cliente.domain.collections.LogClientes;

public interface ClienteMongoRepository extends MongoRepository<LogClientes, UUID> {

}
