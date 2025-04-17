package tn.esprit.spring.utils;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import tn.esprit.spring.entities.Subscription;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PdfGenerator {
    public static void generateReclamationPdfStream(Subscription reclamation, OutputStream outputStream) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            document.add(new Paragraph("Subscription N° : " + reclamation.getNumSub()));
            document.add(new Paragraph("StartDate : " + reclamation.getEndDate()));
            document.add(new Paragraph("EndDate : " + reclamation.getStartDate()));
            document.add(new Paragraph("Price : " + reclamation.getPrice()));
            document.add(new Paragraph("Type : " + reclamation.getTypeSub()));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
