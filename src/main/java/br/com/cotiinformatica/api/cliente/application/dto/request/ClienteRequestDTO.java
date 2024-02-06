package br.com.cotiinformatica.api.cliente.application.dto.request;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.validator.constraints.br.CPF;

import br.com.cotiinformatica.api.cliente.domain.entities.Endereco;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class ClienteRequestDTO {
	
	private UUID idCliente;
	@NotBlank(message = "Nome é Obrigatorio")
	@Size(min = 8, max = 100, message = "Mínimo de 8 e máximo de 100 caracteres")
	private String nome;
	@NotBlank(message = "E-mail é Obrigatorio")
	@Email(message = "Formato de email válido")
	private String email;
	@NotBlank(message = "O CPF é obrigatório")
	@Pattern(regexp = "^[0-9]{11}$", message = "O CPF deve ter 11 dígitos numéricos.")
	@CPF(message = "CPF está inválido")
	private String cpf;
	@NotBlank(message = "Data Nascimento é Obrigatorio")
	private String dataNascimento;
	private List<Endereco> enderecos = new ArrayList<>(); 

}
