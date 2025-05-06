package com.carmotors.util;

import com.carmotors.model.Cliente;
import com.carmotors.model.Factura;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FacturaGenerator {
    private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private static final Font SUBTITLE_FONT = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.HELVETICA, 10);
    private static final Font BOLD_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);

    public static String generarFactura(Factura factura) { // Cambiamos el tipo de retorno a String
        Document document = new Document();
        String rutaArchivo = ""; // Inicializamos la variable para almacenar la ruta
        try {
            String filename = "factura_" + factura.getNumeroFactura() + ".pdf";
            Path path = Paths.get(filename); // Obtener la ruta del archivo
            rutaArchivo = path.toAbsolutePath().toString(); // Convertir a ruta absoluta
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            // Encabezado
            addHeader(document, factura);

            // Información del cliente
            addClientInfo(document, factura);

            // Detalles de la factura
            addInvoiceDetails(document, factura);

            // Totales
            addTotals(document, factura);

            // Pie de página con QR
            addFooter(document, factura);

            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            return null; // Devolvemos null en caso de error
        }
        return rutaArchivo; // Devolvemos la ruta del archivo generado
    }

    private static void addFooter(Document document, Factura factura) throws DocumentException {

            Paragraph footer = new Paragraph();
            footer.add(new Paragraph("TALLER AUTOMOTRIZ MOTORES & RUEDAS", TITLE_FONT));
            footer.add(new Paragraph("NIT: 900.123.456-7", NORMAL_FONT));
            footer.add(new Paragraph("Dirección: Calle 123 #45-67, Bogotá", NORMAL_FONT));
            footer.add(new Paragraph("Teléfono: +57 1 2345678", NORMAL_FONT));
            footer.add(new Paragraph(" "));
            footer.add(new Paragraph("FACTURA ELECTRÓNICA", SUBTITLE_FONT));
            footer.add(new Paragraph("Número: " + factura.getNumeroFactura(), NORMAL_FONT));
            footer.add(new Paragraph("Fecha: " + factura.getFechaEmision().toString(), NORMAL_FONT));
            footer.add(new Paragraph("CUFE: " + factura.getCUFE(), NORMAL_FONT));
            footer.add(new Paragraph(" "));

            document.add(footer);
    }

    private static void addTotals(Document document, Factura factura) throws DocumentException {
        Paragraph totals = new Paragraph();
        totals.add(new Paragraph("Subtotal: " + factura.getSubtotal(), NORMAL_FONT));
        totals.add(new Paragraph("Impuestos: " + factura.getImpuestos(), NORMAL_FONT));
        totals.add(new Paragraph("Total: " + factura.getTotal(), NORMAL_FONT));
        document.add(totals);   
    }

    private static void addInvoiceDetails(Document document, Factura factura) throws DocumentException {
        Paragraph invoiceDetails = new Paragraph();
        invoiceDetails.add(new Paragraph("Detalle de la factura", SUBTITLE_FONT));
        invoiceDetails.add(new Paragraph(" "));
        document.add(invoiceDetails);
    }

    private static void addClientInfo(Document document, Factura factura) throws DocumentException {
        Cliente cliente = factura.getIdCliente();
        if (cliente != null) {
            Paragraph clientInfo = new Paragraph();
            clientInfo.add(new Paragraph("Cliente: " + cliente.getNombre(), NORMAL_FONT));
            clientInfo.add(new Paragraph("Identificación: " + cliente.getIdentificacion(), NORMAL_FONT));
            // Más campos si quieres
            clientInfo.add(new Paragraph(" "));
            document.add(clientInfo);
        }
    }

    private static void addHeader(Document document, Factura factura) throws DocumentException {
        Paragraph header = new Paragraph();
        header.add(new Paragraph("TALLER AUTOMOTRIZ MOTORES & RUEDAS", TITLE_FONT));
        header.add(new Paragraph("NIT: 900.123.456-7", NORMAL_FONT));
        header.add(new Paragraph("Dirección: Calle 123 #45-67, Bogotá", NORMAL_FONT));
        header.add(new Paragraph("Teléfono: +57 1 2345678", NORMAL_FONT));
        header.add(new Paragraph(" "));
        header.add(new Paragraph("FACTURA ELECTRÓNICA", SUBTITLE_FONT));
        header.add(new Paragraph("Número: " + factura.getNumeroFactura(), NORMAL_FONT));
        header.add(new Paragraph("Fecha: " + factura.getFechaEmision().toString(), NORMAL_FONT));
        header.add(new Paragraph("CUFE: " + factura.getCUFE(), NORMAL_FONT));
        header.add(new Paragraph(" "));

        document.add(header);
    }

}
