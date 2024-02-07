package br.com.cotiinformatica.api.cliente.reports;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

public class CustomFooter extends PdfPageEventHelper {

    public void onEndPage(PdfWriter writer, Document document) {
        Paragraph footer = new Paragraph("Este é um rodapé personalizado.",
                                         new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL));
        footer.setAlignment(Element.ALIGN_CENTER);
        try {
			document.add(footer);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

