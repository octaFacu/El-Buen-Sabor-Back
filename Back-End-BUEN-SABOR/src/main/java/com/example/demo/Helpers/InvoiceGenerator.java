package com.example.demo.Helpers;

import com.example.demo.Entidades.Factura;
import com.example.demo.Entidades.PedidoHasProducto;
import com.example.demo.Entidades.Producto;
import com.example.demo.Entidades.Proyecciones.ProyeccionProductosDePedido;
import com.example.demo.Repository.PedidoRepository;
import com.example.demo.Services.ImpPedidoService;
import com.example.demo.Services.PedidoService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class InvoiceGenerator {

    @Value("${rutaComprobantes}")
    private String pdfFolderPath;

    private final ApplicationContext context;

    @Autowired
    public InvoiceGenerator(ApplicationContext context) {
        this.context = context;
    }

    public void generatePDFInvoice(Factura datosFactura) {


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy-HHmmss");
        String formattedDateTime = datosFactura.getPedido().getFechaPedido().toLocalDateTime().format(formatter);

        String fileName = "FC"+datosFactura.getNumeroFactura()+"-"+formattedDateTime;

        String filePath = "D:/DOCUMENTOS/Desktop/ElBuenSabor/Comprobantes/FacturasPDF";
        String fullPath = filePath + "/"+fileName+".pdf";

        Document document = new Document();

        // Set up fonts
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.ORANGE);
        Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.DARK_GRAY);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);


        try {

            //Create folder if it does not exist
            Path path = Paths.get(filePath);
            Files.createDirectories(path);

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fullPath));
            document.open();


            Paragraph title = new Paragraph("Invoice: #" + datosFactura.getTipo() + "-" +
                    formateoNumeroFactura(Integer.parseInt(datosFactura.getNumeroFactura())) + datosFactura.getId() + "-" +
                    datosFactura.getPedido().getNumeroPedidoDia(), titleFont);
            document.add(title);
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Cliente: " + datosFactura.getPedido().getCliente().getUsuario().getApellido() + " " + datosFactura.getPedido().getCliente().getUsuario().getNombre(), normalFont));
            document.add(new Paragraph("Fecha: " + datosFactura.getPedido().getFechaPedido().toString(), normalFont));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Detalle", subtitleFont));

            document.add(new Paragraph("\n"));
            PedidoService servicePedido = context.getBean(PedidoService.class);

            // Create and format table for product details
            PdfPTable table = new PdfPTable(3); // 3 columns
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

// Add table headers
            table.addCell(new PdfPCell(new Phrase("Descripción", subtitleFont)));
            table.addCell(new PdfPCell(new Phrase("Cantidad", subtitleFont)));
            table.addCell(new PdfPCell(new Phrase("Precio", subtitleFont)));
            // Agregar detalles del producto a la tabla
            for (PedidoHasProducto producto : servicePedido.buscarPedidoProductos((long) datosFactura.getPedido().getId())) {
                String denominacion = producto.getProducto().getDenominacion();
                String cantidad = String.valueOf(producto.getCantidad());

                if(denominacion != null){
                    table.addCell(new PdfPCell(new Phrase(denominacion, normalFont)));
                }else{
                    table.addCell(new PdfPCell(new Phrase("Producto", normalFont)));
                }

                if(cantidad != null){
                    table.addCell(new PdfPCell(new Phrase(cantidad, normalFont)));
                }else{
                    table.addCell(new PdfPCell(new Phrase("1", normalFont)));
                }

                table.addCell(new PdfPCell(new Phrase("$" + String.valueOf(producto.getProducto().getPrecioTotal()), normalFont)));
            }

            document.add(table);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Descuento: $"+datosFactura.getMontoDescuento()));
            document.add(new Paragraph("Total: $" + datosFactura.getPedido().getPrecioTotal()));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Gracias por su compra! Disfrute su comida"));
            PdfContentByte contentByte = writer.getDirectContent();
            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 65, Font.BOLD, BaseColor.LIGHT_GRAY);
            ColumnText.showTextAligned(contentByte, Element.ALIGN_CENTER, new Phrase("EL BUEN SABOR"), 300, 400, 30);


        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } catch (com.itextpdf.text.DocumentException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            document.close();
        }
    }


    public void generatePDFNotaCredito(Factura datosFactura) {


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy-HHmmss");
        String formattedDateTime =  datosFactura.getPedido().getFechaPedido().toLocalDateTime().format(formatter);

        String fileName = "NC-"+datosFactura.getNumeroFactura() +"-"+formattedDateTime;

        String filePath = "D:/DOCUMENTOS/Desktop/ElBuenSabor/Comprobantes/NotasCreditoPDF";
        String fullPath = filePath + "/"+fileName+".pdf";

        Document document = new Document();
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.ORANGE);
        Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.DARK_GRAY);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        try {

            Path path = Paths.get(filePath);
            Files.createDirectories(path);

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fullPath));
            document.open();


            Paragraph title = new Paragraph("Invoice: #" + datosFactura.getTipo() + "-" +
                    formateoNumeroFactura(Integer.parseInt(datosFactura.getNumeroFactura())) + datosFactura.getId() + "-" +
                    datosFactura.getPedido().getNumeroPedidoDia(), titleFont);
            document.add(title);
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Cliente: " + datosFactura.getPedido().getCliente().getUsuario().getApellido() + " " + datosFactura.getPedido().getCliente().getUsuario().getNombre(), normalFont));
            document.add(new Paragraph("Fecha: " + datosFactura.getPedido().getFechaPedido().toString(), normalFont));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Detalle", subtitleFont));

            document.add(new Paragraph("\n"));
            PedidoService servicePedido = context.getBean(PedidoService.class);

            // Create and format table for product details
            PdfPTable table = new PdfPTable(3); // 3 columns
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

// Add table headers
            table.addCell(new PdfPCell(new Phrase("Descripción", subtitleFont)));
            table.addCell(new PdfPCell(new Phrase("Cantidad", subtitleFont)));
            table.addCell(new PdfPCell(new Phrase("Precio", subtitleFont)));
            // Agregar detalles del producto a la tabla

                table.addCell(new PdfPCell(new Phrase("Devolución por Factura n"+datosFactura.getNumeroFactura(), normalFont)));
                table.addCell(new PdfPCell(new Phrase("1", normalFont)));
                table.addCell(new PdfPCell(new Phrase("$" + String.valueOf(datosFactura.getPedido().getPrecioTotal()), normalFont)));

            document.add(table);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Total: $" + datosFactura.getPedido().getPrecioTotal()));
            document.add(new Paragraph("\n"));

            PdfContentByte contentByte = writer.getDirectContent();
            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 65, Font.BOLD, BaseColor.LIGHT_GRAY);
            ColumnText.showTextAligned(contentByte, Element.ALIGN_CENTER, new Phrase("EL BUEN SABOR"), 300, 400, 30);


        } catch ( IOException e) {
            e.printStackTrace();
        } catch (com.itextpdf.text.DocumentException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            document.close();
        }
    }



    private static String formateoNumeroFactura(int numeroFactura) {
        return String.format("%04d", numeroFactura);
    }
}
