package br.com.cotiinformatica.api.cliente;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.junit.jupiter.api.Order;
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
class ClientePutTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;
	
	
	
	
	@Test
	@Order(2)
	public void clientePutTest() throws Exception {
	    Faker faker = new Faker(new Locale("pt-BR"));
	    Cliente dto = new Cliente();
	    UUID clienteIdExistente = UUID.fromString("610cf5b2-fff1-437d-ad91-c55fdcb81ef4"); // Substitua pelo ID existente na base de dados
	    dto.setIdCliente(clienteIdExistente);
	    dto.setNome(faker.name().fullName());
	    String cpf = "179.949.550-70";
	    dto.setCpf(cpf.replace(".", "").replace("-", ""));
	    dto.setEmail(faker.internet().emailAddress());
	    dto.setDataNascimento("1986-06-15");

	    List<Endereco> enderecos = new ArrayList<>();
	    Endereco enderecoExistente = new Endereco();
	    UUID enderecoIdExistente = UUID.fromString("0c8cbaf0-6cd5-4437-9cd2-d8cb066d6289"); // Substitua pelo ID existente na base de dados
	    enderecoExistente.setIdEndereco(enderecoIdExistente);
	    enderecoExistente.setLogradouro(faker.address().streetName());
	    enderecoExistente.setNumero(faker.address().buildingNumber());
	    enderecoExistente.setBairro(faker.address().streetSuffix());
	    enderecoExistente.setCidade(faker.address().city());
	    enderecoExistente.setUf(faker.address().stateAbbr());
	    enderecoExistente.setCep(faker.address().zipCode());
	    enderecoExistente.setCliente(dto);
	    enderecos.add(enderecoExistente);

	    dto.setEnderecos(enderecos);

	    mockMvc.perform(put("/api/clientes/610cf5b2-fff1-437d-ad91-c55fdcb81ef4", clienteIdExistente, enderecoIdExistente) // caminho do endpoint com os IDs do cliente e do endereço
	            .contentType("application/json") // formato dos dados
	            .content(objectMapper.writeValueAsString(dto))) // serializando os dados
	            .andExpect(status().isOk()); // verificando o status da resposta

	    assertNotNull(dto);
	}


}
