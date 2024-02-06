package br.com.cotiinformatica.api.cliente;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
    public void testDeletarClienteExistente() throws Exception {
        // Gerar um ID de cliente fictício
        UUID clienteIdExistente = UUID.fromString("477d8c8d-ed0b-4b3c-8a25-49a146cc4fe5"); // Substitua pelo ID existente na base de dados

        // Realizar uma chamada DELETE para deletar o cliente pelo ID
        mockMvc.perform(delete("/api/clientes/{idCliente}", clienteIdExistente))
                .andExpect(status().isOk());
    }
	
	


}
