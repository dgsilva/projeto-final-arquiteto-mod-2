//package br.com.cotiinformatica.api.cliente;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.UUID;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.web.multipart.MultipartFile;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import br.com.cotiinformatica.api.cliente.infrastructure.service.ClienteService;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class ClientePutFotoTests {
//
//	@Autowired
//    MockMvc mockMvc;
//
//    @MockBean
//    ClienteService clienteService;
//
//    @Test
//    public void testUploadFotoCliente() throws Exception {
//        // Dados fictícios do cliente
//        UUID clienteIdExistente = UUID.fromString("610cf5b2-fff1-437d-ad91-c55fdcb81ef4"); // Substitua pelo ID existente na base de dados
//        
//        // Carregar a imagem do arquivo
//        Path imagePath = Paths.get("src/test/java/testes-desenvolvimento.jpg");
//        byte[] content = Files.readAllBytes(imagePath);
//        
//        // Criando um MockMultipartFile com dados da imagem
//        MockMultipartFile imagem = new MockMultipartFile("imagem", "testes-desenvolvimento.jpg", "image/jpeg", content);
//
//        // Mockando o comportamento do serviço
//        when(clienteService.atualizarFotoCliente(any(UUID.class), any(MultipartFile.class)))
//            .thenReturn(ResponseEntity.ok().build());
//
//        // Construindo a solicitação PUT corretamente
//        MockHttpServletRequestBuilder requestBuilder = 
//                put("/api/clientes/foto/{idCliente}/atualizar-foto", clienteIdExistente)
//                .contentType(MediaType.MULTIPART_FORM_DATA)
//                .content(imagem.getBytes());
//        
//        // Executando a solicitação e verificando o status da resposta
//        mockMvc.perform(requestBuilder)
//                .andExpect(status().isOk());
//    }
//
//}