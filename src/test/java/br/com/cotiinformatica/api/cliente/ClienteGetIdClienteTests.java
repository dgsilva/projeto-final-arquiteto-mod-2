package br.com.cotiinformatica.api.cliente;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
class ClienteGetIdClienteTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;
	
	 @Test
	    public void testConsultarClienteExistente() throws Exception {
	        // Gerar um ID de cliente fictício
	        UUID clienteIdExistente = UUID.fromString("ID_EXISTENTE_DO_CLIENTE"); // Substitua pelo ID existente na base de dados

	        // Realizar uma chamada GET para consultar o cliente pelo ID
	        mockMvc.perform(get("/api/clientes/{idCliente}", clienteIdExistente)
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())
	                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	                // Adicione outras verificações conforme necessário, como verificar o conteúdo da resposta
	                .andExpect(jsonPath("$.idCliente").value(clienteIdExistente.toString())); // Verificar se o ID do cliente na resposta é o mesmo que o ID gerado
	    }
	
	


}
