package br.com.cotiinformatica.api.cliente;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

import br.com.cotiinformatica.api.cliente.domain.entities.Cliente;
import br.com.cotiinformatica.api.cliente.infrastructure.service.ClienteService;

@SpringBootTest
@AutoConfigureMockMvc
class ClientePutFotoTests {

	@Autowired
    MockMvc mockMvc;

    @MockBean
    ClienteService clienteService;

    @Test
    public void clientesAtualizarFotoPutTest() throws Exception {
        // Carregar o arquivo de imagem de teste
        InputStream inputStream = new FileInputStream(ResourceUtils.getFile("classpath:test_image.jpg"));
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test_image.jpg", "image/jpeg", inputStream);

        // Mock do serviço para simular o comportamento
        Cliente cliente = new Cliente();
        cliente.setIdCliente(UUID.randomUUID()); // Suponha que o cliente tenha um ID
        cliente.setNome("Nome do Cliente"); // Suponha que o cliente tenha um nome

//        Mockito.when(clienteService.atualizarFotoCliente(any(), any())).thenReturn(cliente);

        // Realizar a solicitação PUT ao endpoint
//        ResponseEntity<ClienteResponseDTO> responseEntity = ResponseEntity.ok(new ClienteResponseDTO(cliente));
        
        // Verifique se a resposta está OK
        mockMvc.perform(put("/api/clientes/atualizar-foto")
                .param("clienteId", "1") // Suponha que o ID do cliente seja 1
                .param("fotoId", "0c8cbaf0-6cd5-4437-9cd2-d8cb066d6289") // ID da foto
                .content(multipartFile.getBytes())
                .contentType("image/jpeg"))
                .andExpect(status().isOk());
    }
}