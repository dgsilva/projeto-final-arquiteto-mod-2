package br.com.cotiinformatica.api.cliente.reports;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.stereotype.Service;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import br.com.cotiinformatica.api.cliente.domain.entities.Cliente;
import br.com.cotiinformatica.api.cliente.domain.entities.Endereco;

@Service
public class ClienteReports {
    
	
    public ByteArrayInputStream createPdf(List<Cliente> clientes) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 36, 36, 54, 54); // Margens esquerda, direita, superior, inferior
        PdfWriter pdfWriter = PdfWriter.getInstance(document, out);
        document.open();
        
        Paragraph title = new Paragraph("Relatório de Clientes");
        title.setAlignment(Paragraph.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);
        
        PdfPTable table = new PdfPTable(6); // Adicionando uma coluna extra para a foto
        table.setWidthPercentage(100);
        
        table.addCell(createCell("Nome", true));
        table.addCell(createCell("Email", true));
        table.addCell(createCell("CPF", true));
        table.addCell(createCell("Data de Nascimento", true));
        table.addCell(createCell("Endereço", true));
        table.addCell(createCell("Foto", true)); // Cabeçalho para a coluna da foto
        
        for(Cliente cliente : clientes) {
            table.addCell(createCell(cliente.getNome(), false));
            table.addCell(createCell(cliente.getEmail(), false));
            table.addCell(createCell(cliente.getCpf(), false));
            table.addCell(createCell(cliente.getDataNascimento(), false));
            
            String enderecos = "";
            for (Endereco endereco : cliente.getEnderecos()) {
                enderecos += endereco.getLogradouro() + ", ";
                enderecos += endereco.getCidade() + ", ";
                enderecos += endereco.getBairro() + ", ";
                enderecos += endereco.getCep() + ", ";
                enderecos += endereco.getNumero() + ", ";
                enderecos += endereco.getUf() + "\n"; // Quebra de linha entre endereços
            }
            
            table.addCell(createCell(enderecos, false));
            
            byte[] foto = cliente.getFoto(); // Obter a matriz de bytes da foto
            if (foto != null) { // Verificar se a matriz de bytes não é nula
                table.addCell(createImageCell(foto)); // Adicionar a célula da imagem à tabela
            } else {
                table.addCell(createCell("", false)); // Se a foto for nula, adicionar uma célula vazia
            }
        }
        
        document.add(table);
        document.close();
        pdfWriter.close();

        return new ByteArrayInputStream(out.toByteArray());
    }

    // Métodos auxiliares
    private PdfPCell createCell(String content, boolean header) {
        PdfPCell cell = new PdfPCell(new Paragraph(content));
        // Configurações de formatação omitidas por brevidade
        return cell;
    }

    private PdfPCell createImageCell(byte[] imageBytes) throws BadElementException {
        try {
            Image image = Image.getInstance(imageBytes);
            image.scaleToFit(100, 100);
            PdfPCell cell = new PdfPCell(image, true);
            return cell;
        } catch (Exception e) {
            throw new BadElementException(e);
        }
    }
    
}
