package Arona.sociedadAgricola.service.impl;

import Arona.sociedadAgricola.model.Envio;
import Arona.sociedadAgricola.model.EnvioDetalle;
import Arona.sociedadAgricola.repository.EnvioDetalleRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

/**
 * Servicio para generar documentos PDF de envíos.
 * HU5: El sistema debe generar un documento con el detalle de cada envío.
 */
@Service
public class PdfGeneratorService {

    @Autowired
    private EnvioDetalleRepository envioDetalleRepository;

    public byte[] generarDocumentoEnvio(Envio envio) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, baos);
        document.open();

        // Título
        Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
        Paragraph titulo = new Paragraph("SOCIEDAD AGRÍCOLA ARONA S.A.", titleFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);

        Font subtitleFont = new Font(Font.HELVETICA, 14, Font.BOLD);
        Paragraph subtitulo = new Paragraph("Documento de Envío de Exportación", subtitleFont);
        subtitulo.setAlignment(Element.ALIGN_CENTER);
        document.add(subtitulo);

        document.add(new Paragraph(" "));

        // Información del envío
        Font labelFont = new Font(Font.HELVETICA, 11, Font.BOLD);
        Font valueFont = new Font(Font.HELVETICA, 11, Font.NORMAL);

        document.add(createInfoLine("N° de Envío:", String.valueOf(envio.getIdEnvio()), labelFont, valueFont));
        document.add(createInfoLine("País Destino:", envio.getPaisDestino(), labelFont, valueFont));
        document.add(createInfoLine("Tipo de Cultivo:", envio.getTipoCultivo(), labelFont, valueFont));
        document.add(createInfoLine("Cantidad Total (Kg):", 
                envio.getCantidadKg() != null ? envio.getCantidadKg().toString() : "N/A", labelFont, valueFont));
        document.add(createInfoLine("Estado:", envio.getEstadoEnvio(), labelFont, valueFont));
        document.add(createInfoLine("Fecha de Envío:", 
                envio.getFechaEnvio() != null ? envio.getFechaEnvio().toString() : "Pendiente", labelFont, valueFont));
        document.add(createInfoLine("Fecha de Entrega:", 
                envio.getFechaEntrega() != null ? envio.getFechaEntrega().toString() : "Pendiente", labelFont, valueFont));

        document.add(new Paragraph(" "));

        // Tabla de detalle
        List<EnvioDetalle> detalles = envioDetalleRepository.findByIdEnvio(envio.getIdEnvio());

        if (!detalles.isEmpty()) {
            Paragraph detalleTitle = new Paragraph("Detalle del Envío", subtitleFont);
            document.add(detalleTitle);
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1f, 2f, 2f});

            // Encabezados
            Font headerFont = new Font(Font.HELVETICA, 10, Font.BOLD);
            addTableHeader(table, "N°", headerFont);
            addTableHeader(table, "ID Clasificación", headerFont);
            addTableHeader(table, "Cantidad (Kg)", headerFont);

            // Filas
            Font cellFont = new Font(Font.HELVETICA, 10, Font.NORMAL);
            int i = 1;
            for (EnvioDetalle detalle : detalles) {
                addTableCell(table, String.valueOf(i++), cellFont);
                addTableCell(table, String.valueOf(detalle.getIdClasificacion()), cellFont);
                addTableCell(table, detalle.getCantidadKg() != null ? detalle.getCantidadKg().toString() : "N/A", cellFont);
            }

            document.add(table);
        }

        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        // Pie de página
        Font footerFont = new Font(Font.HELVETICA, 9, Font.ITALIC);
        Paragraph footer = new Paragraph("Documento generado automáticamente - Sociedad Agrícola Arona S.A. - " 
                + LocalDate.now(), footerFont);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);

        document.close();
        return baos.toByteArray();
    }

    private Paragraph createInfoLine(String label, String value, Font labelFont, Font valueFont) {
        Paragraph p = new Paragraph();
        p.add(new Chunk(label + " ", labelFont));
        p.add(new Chunk(value, valueFont));
        return p;
    }

    private void addTableHeader(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        table.addCell(cell);
    }

    private void addTableCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(4);
        table.addCell(cell);
    }
}
