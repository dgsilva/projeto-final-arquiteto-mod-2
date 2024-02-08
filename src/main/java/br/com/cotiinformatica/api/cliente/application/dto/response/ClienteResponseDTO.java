package br.com.cotiinformatica.api.cliente.application.dto.response;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import br.com.cotiinformatica.api.cliente.domain.entities.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponseDTO {

	private UUID idCliente;
	private String nome;
	private String email;
	private String cpf;
	private String dataNascimento;
	private List<Endereco> enderecos = new ArrayList<>();
	private byte[] foto;
}
