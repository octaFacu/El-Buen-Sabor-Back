package com.example.demo.Helpers;

import com.example.demo.Entidades.Factura;
import com.example.demo.Entidades.PedidoHasProducto;
import com.example.demo.Entidades.Producto;
import com.example.demo.Entidades.Proyecciones.ProyeccionProductosDePedido;
import com.example.demo.Repository.PedidoRepository;
import com.example.demo.Services.ImpPedidoService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class InvoiceGenerator {

    @Value("${rutaComprobantes}")
    private String pdfFolderPath;



    @Autowired
    PedidoRepository repository;

    public void generatePDFInvoice(Factura datosFactura) {


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy-HH:mm:ss");
        String formattedDateTime = datosFactura.getPedido().getFechaPedido().toLocalDateTime().format(formatter);

        String fileName = "FC"+datosFactura.getNumeroFactura() +"-"+formattedDateTime;

        String filePath = pdfFolderPath + "/FacturasPDF";
        String fullPath = filePath + "/"+fileName;

        Document document = new Document();

        try {

            //Create folder if it does not exist
            Path path = Paths.get(filePath);
            Files.createDirectories(path);

            PdfWriter.getInstance(document, new FileOutputStream(fullPath));
            document.open();


            // Add content to the PDF
            document.add(new Paragraph("Invoice: #" + datosFactura.getTipo() + "-" +
                    formateoNumeroFactura(Integer.parseInt(datosFactura.getNumeroFactura())) + datosFactura.getId() + "-" +
                    datosFactura.getPedido().getNumeroPedidoDia()));

            document.add(new Paragraph("Cliente: " + datosFactura.getPedido().getCliente().getUsuario().getApellido() + " " + datosFactura.getPedido().getCliente().getUsuario().getNombre()));
            document.add(new Paragraph("Fecha: " + datosFactura.getPedido().getFechaPedido().toString()));

            document.add(new Paragraph("Detalle"));
            document.add(new Paragraph("Descripción\tCantidad\tPrecio"));


            //Tomar todos los productos de un pedido
            //for (PedidoHasProducto producto : datosFactura.getPedido()) {
            for (PedidoHasProducto producto : buscarPedidoProducto((long) datosFactura.getPedido().getId())) {
                document.add(new Paragraph(producto.getProducto().getDenominacion() + "\t" +
                        producto.getCantidad() + "\t$" + producto.getProducto().getPrecioTotal()));
            }

            document.add(new Paragraph("Descuento: $"+datosFactura.getMontoDescuento()));
            document.add(new Paragraph("Total: $" + datosFactura.getPedido().getPrecioTotal()));

            document.add(new Paragraph("Gracias por su compra! Disfrute su comida"));

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


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy-HH:mm:ss");
        String formattedDateTime =  datosFactura.getPedido().getFechaPedido().toLocalDateTime().format(formatter);

        String fileName = "NC-"+datosFactura.getNumeroFactura() +"-"+formattedDateTime;

        String filePath = pdfFolderPath + "/NotasCreditoPDF";
        String fullPath = filePath + "/"+fileName;

        Document document = new Document();

        try {

            //Create folder if it does not exist
            Path path = Paths.get(filePath);
            Files.createDirectories(path);

            PdfWriter.getInstance(document, new FileOutputStream(fullPath));
            document.open();


            // Add content to the PDF
            document.add(new Paragraph("Nota de Crédito: #" + datosFactura.getTipo() + "-" +
                    formateoNumeroFactura(Integer.parseInt(datosFactura.getNumeroFactura())) + datosFactura.getId()));

            document.add(new Paragraph("Cliente: " + datosFactura.getPedido().getCliente().getUsuario().getApellido() + " " + datosFactura.getPedido().getCliente().getUsuario().getNombre()));
            document.add(new Paragraph("Fecha: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString()));

            document.add(new Paragraph("Detalle"));
            document.add(new Paragraph("Descripción\tCantidad\tPrecio"));



                document.add(new Paragraph("Devolución por compra de fecha "+datosFactura.getPedido().getFechaPedido().toString() + "\t" +
                        "1" + "\t$" + datosFactura.getPedido().getPrecioTotal()));

            document.add(new Paragraph("Total: $" + datosFactura.getPedido().getPrecioTotal()));


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










    public List<PedidoHasProducto> buscarPedidoProducto(Long idPedido) throws Exception{

        try{
            List<PedidoHasProducto> productosPedido = repository.buscarPedidoProductos(idPedido);

            return productosPedido;
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    private static String formateoNumeroFactura(int numeroFactura) {
        return String.format("%04d", numeroFactura);
    }
}
