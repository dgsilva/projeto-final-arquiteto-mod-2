package br.com.cotiinformatica.api.cliente;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import br.com.cotiinformatica.api.cliente.domain.entities.Cliente;
import br.com.cotiinformatica.api.cliente.infrastructure.service.ClienteService;
import br.com.cotiinformatica.api.cliente.reports.ClienteReports;

@SpringBootTest
@AutoConfigureMockMvc
class ClienteRelatorioTests {

	@Autowired
    MockMvc mockMvc;

    @MockBean
    ClienteService clienteService;

    @MockBean
    ClienteReports clienteReports;

    @Test
    public void clientesGetRelatorioTest() throws Exception {
        // Mock dos dados do relatório
        List<Cliente> clientes = new ArrayList<>();
        // Preencha a lista de clientes com dados de teste

        // Mock do serviço para retornar os dados do relatório
        Mockito.when(clienteService.findAll()).thenReturn(clientes);

        // Mock do relatório PDF
        byte[] relatorioBytes = "Relatório PDF".getBytes();
        ByteArrayInputStream relatorioStream = new ByteArrayInputStream(relatorioBytes);
        Mockito.when(clienteReports.createPdf(clientes)).thenReturn(relatorioStream);

        // Realizar a solicitação GET ao endpoint
        MvcResult result = mockMvc.perform(get("/api/clientes/gerar-relatorio"))
                .andExpect(status().isOk()) // Verificar se a resposta está OK
                .andReturn();

        // Verificar se a resposta contém o conteúdo do relatório PDF
        MockHttpServletResponse response = result.getResponse();
        byte[] contentBytes = response.getContentAsByteArray();
        // Realize as asserções necessárias para verificar o conteúdo do relatório
    }
}