package br.com.cotiinformatica.api.cliente.domain.collections;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Data;

@Data
@Document(collection = "logClientes")
public class LogClientes {
	@Id
	private UUID id;
	private Instant dataHora;
	private String operacao;
	private String descriao;
}
