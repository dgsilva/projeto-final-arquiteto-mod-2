package br.com.cotiinformatica.api.cliente;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.cotiinformatica.api.cliente.domain.entities.Cliente;
import br.com.cotiinformatica.api.cliente.domain.entities.Endereco;

@SpringBootTest
@AutoConfigureMockMvc
class ClientesCreateTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	public void clientePostTest() throws Exception {
		Faker faker = new Faker(new Locale("pt-BR"));
		Cliente dto = new Cliente();
		dto.setIdCliente(UUID.randomUUID());
		dto.setNome(faker.name().fullName());
		String cpf = "733.939.200-90";
		dto.setCpf(cpf.replace(".","").replace("-",""));
		dto.setEmail(faker.internet().emailAddress());
		dto.setDataNascimento("1986-06-15");
		List<Endereco> enderecos = new ArrayList<>();
		for (int i = 0; i < 1; i++) {
			Endereco endereco = new Endereco();
			endereco.setIdEndereco(UUID.randomUUID()); // Gerar um ID único para cada endereço
			endereco.setLogradouro(faker.address().streetName());
			endereco.setNumero(faker.address().buildingNumber());
			endereco.setBairro(faker.address().streetSuffix());
			endereco.setCidade(faker.address().city());
			endereco.setUf(faker.address().stateAbbr());
			endereco.setCep(faker.address().zipCode());
			endereco.setCliente(dto);
			enderecos.add(endereco);
			
		}

		dto.setEnderecos(enderecos);
		 mockMvc.perform(post("/api/clientes") // caminho do endpoint
				.contentType("application/json") // formato dos dados
				.content(objectMapper.writeValueAsString(dto))) // serializando os dados
				.andExpect(status().isCreated()); // verificando o status da resposta
		
		
		assertNotNull(dto);
				
	}
}
