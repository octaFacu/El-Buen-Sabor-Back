package com.example.demo.EstadisticasExcel;

import com.example.demo.Entidades.Proyecciones.ProyeccionEstadisticaClienteTotalPedidos;
import com.example.demo.Entidades.Proyecciones.ProyeccionHistorialPedidoUsuario;
import com.example.demo.Entidades.Proyecciones.ProyeccionProductosDePedido;
import com.example.demo.EstadisticasExcel.Estilos.EstilosRankingClientes;
import com.example.demo.Services.ClienteService;
import com.example.demo.Services.PedidoService;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

@Service
public class ReporteExcelServiceImpl implements PosicionesCeldasExcel {

    @Autowired
    private ClienteService servicioCliente;

    @Autowired
    private PedidoService servicioPedido;

    EstilosRankingClientes estilos = new EstilosRankingClientes();

    // este metodo es el que se encarga de crear el libro y cerrarlo una ves creado
    public XSSFWorkbook  generateExcelReport() throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(); // crea el libro
        XSSFSheet hoja = workbook.createSheet("Ranking de clientes"); // crea la hoja principal

        CabeceraRanking(hoja);
        ColumnasRanking(hoja, workbook);

        return workbook;
    }
     // creamos la cabezera de la hoja Principal
     private void CabeceraRanking(XSSFSheet hoja) {
         XSSFRow filaCabecera = hoja.createRow(0);
         filaCabecera.createCell(PosicionesCeldasExcel.ID_CLIENTE).setCellValue("id cliente");
         filaCabecera.createCell(PosicionesCeldasExcel.NOMBRE).setCellValue("Nombre");
         filaCabecera.createCell(PosicionesCeldasExcel.PEDIDOS).setCellValue("Pedidos");
         filaCabecera.createCell(PosicionesCeldasExcel.IMPORTE_TOTAL).setCellValue("Importe Total");
         filaCabecera.createCell(PosicionesCeldasExcel.VER_HISTORIAL).setCellValue("Ver Historial");
     }
    // Crea las filas y llena los datos de clientes en la hoja de ranking
    private void ColumnasRanking(XSSFSheet hoja, XSSFWorkbook workbook) throws Exception {
        List<ProyeccionEstadisticaClienteTotalPedidos> rankingClientes = servicioCliente.ProyeccionEstadisticaClienteTotalPedidosSinPages();

        for (int i = 0; i < rankingClientes.size(); i++) {
            XSSFRow filaDatos = hoja.createRow(i + 1); // Crear una nueva fila
            llenarDatosCliente(filaDatos, rankingClientes.get(i)); // llenamos la fila con los datos del cliente
            generarHistorialCliente(workbook, rankingClientes.get(i).getId_cliente()); // Generar la hoja de historial para el cliente

            // creamos un hipervínculo para cada historial de cada cliente
            Hyperlink hyperlink = generarHipervinculo(workbook, rankingClientes.get(i).getId_cliente());
            filaDatos.createCell(PosicionesCeldasExcel.VER_HISTORIAL).setCellValue("Ver Historial");
            filaDatos.getCell(PosicionesCeldasExcel.VER_HISTORIAL).setHyperlink(hyperlink); // Agregamos el hipervínculo a la celda
        }

        estilos.aplicarEstilosHojaPrincipal(hoja); // Aplicar estilos a la hoja de ranking de clientes
    }
    // aca llenamos cada fila con datos de cada cliente
    private void llenarDatosCliente(XSSFRow filaDatos, ProyeccionEstadisticaClienteTotalPedidos cliente) {
        filaDatos.createCell(PosicionesCeldasExcel.ID_CLIENTE).setCellValue(cliente.getId_cliente());
        filaDatos.createCell(PosicionesCeldasExcel.NOMBRE).setCellValue(cliente.getNombre());
        filaDatos.createCell(PosicionesCeldasExcel.PEDIDOS).setCellValue(cliente.getCantidad_pedidos());
        filaDatos.createCell(PosicionesCeldasExcel.IMPORTE_TOTAL).setCellValue(cliente.getImporte_total());
    }
    // generamos los hipervinculos
    private Hyperlink generarHipervinculo(XSSFWorkbook workbook, long clientId) {
        CreationHelper creationHelper = workbook.getCreationHelper();
        HyperlinkType hyperlinkType = HyperlinkType.DOCUMENT; // aca estamos indicando los hipervinculos son del mismo documento

        String hojaHistorial = "Historial_" + clientId; // nombre unico para cada hoja

        Hyperlink hyperlink = creationHelper.createHyperlink(hyperlinkType);
        hyperlink.setAddress("'" + hojaHistorial + "'!A1"); // aca estamos seteando el hipervinculo en la ubicacion específica por eso ponemos 2 veces el nombre

        return hyperlink;
    }
    // generamos el historial para cada cliente específico
    private void generarHistorialCliente(XSSFWorkbook workbook, long clientId) throws Exception {
        String hojaHistorial = "Historial_" + clientId; // nombre unico para nuestra hoja
        XSSFSheet hojaHistorialPedidos = workbook.createSheet(hojaHistorial); // creamos la hoja

        List<ProyeccionHistorialPedidoUsuario> historialUsuarios = servicioCliente.historialPedidoClienteSinPage(clientId);
        int rowIndex = 0;
        int contador = 1;

        CabeceraHistorial(hojaHistorialPedidos); // creamos la cabecera
        // formateamos la fecha y hora
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd MMMM yyyy", new Locale("es", "ES"));
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
        // iniciamos el bucle dentro del historial de nuestro usuario
        for (ProyeccionHistorialPedidoUsuario historialUsuario : historialUsuarios) {
            rowIndex++;
            XSSFRow filaHistorial = hojaHistorialPedidos.createRow(rowIndex);
            // llenamos la fila con los datos del pedido
            llenarHistorial(filaHistorial, historialUsuario,contador, formatoFecha, formatoHora);
            contador++;
            // llenamos los productos del pedido específico y obtenemos la nueva posición de la fila para el siguiente pedido
            rowIndex = ProductosHistorial(hojaHistorialPedidos, rowIndex, (int) historialUsuario.getPedido_id());
        }
        estilos.aplicarEstilosHojaHistorial(hojaHistorialPedidos); // Aplicar los estilos a la hoja de historial
    }

    private void CabeceraHistorial(XSSFSheet hojaHistorialPedidos) { // cabecera de cada historial
        XSSFRow filaCabeceraHistorial = hojaHistorialPedidos.createRow(0);
        filaCabeceraHistorial.createCell(PosicionesCeldasExcel.TIPO_ENVIO).setCellValue("tipo envio");
        filaCabeceraHistorial.createCell(PosicionesCeldasExcel.PRECIO_TOTAL).setCellValue("precio total");
        filaCabeceraHistorial.createCell(PosicionesCeldasExcel.FECHA_PEDIDO).setCellValue("Fecha Pedido");
        filaCabeceraHistorial.createCell(PosicionesCeldasExcel.HORA_PEDIDO).setCellValue("Hora del Pedido");
        filaCabeceraHistorial.createCell(PosicionesCeldasExcel.PRODUCTO).setCellValue("Producto");
        filaCabeceraHistorial.createCell(PosicionesCeldasExcel.DENOMINACION).setCellValue("Denominacion");
        for (int indice = 1; indice <= 5; indice++) {
            hojaHistorialPedidos.autoSizeColumn(indice);
        }
    }


    private void llenarHistorial(XSSFRow filaHistorial, ProyeccionHistorialPedidoUsuario historialUsuario, int contador, SimpleDateFormat fecha, SimpleDateFormat hora) {
        filaHistorial.createCell(0).setCellValue("pedido: " + contador);
        filaHistorial.createCell(PosicionesCeldasExcel.TIPO_ENVIO).setCellValue(historialUsuario.getEs_envio() ? "Envio" : "Retiro Local");
        filaHistorial.createCell(PosicionesCeldasExcel.PRECIO_TOTAL).setCellValue(historialUsuario.getprecio_total());
        filaHistorial.createCell(PosicionesCeldasExcel.FECHA_PEDIDO).setCellValue(fecha.format(historialUsuario.getFecha_pedido()));
        filaHistorial.createCell(PosicionesCeldasExcel.HORA_PEDIDO).setCellValue(hora.format(historialUsuario.getFecha_pedido()));
    }

    // aca ponemos los productos de cada pedido.
    private int ProductosHistorial(XSSFSheet hojaHistorialPedidos, int rowIndex, int pedidoId) throws Exception {
        List<ProyeccionProductosDePedido> productosDePedidos = servicioPedido.getProductosDePedido(pedidoId);

        int originalRowIndex = rowIndex; // Almacenamos el valor original de rowIndex

        for (ProyeccionProductosDePedido producto : productosDePedidos) {
            XSSFRow filaProductos = hojaHistorialPedidos.getRow(originalRowIndex); //esto lo hacemos para obtener la primera fila utilizada y poner el primer producto

            if (filaProductos == null) {
                filaProductos = hojaHistorialPedidos.createRow(originalRowIndex); // aqui empezamos a poner los productos desde la siguiente fila
            }

            filaProductos.createCell(PosicionesCeldasExcel.PRODUCTO).setCellValue("x" + producto.getcantidad());
            filaProductos.createCell(PosicionesCeldasExcel.DENOMINACION).setCellValue(producto.getDenominacion());
            hojaHistorialPedidos.autoSizeColumn(6);

            originalRowIndex++; // incrementamos el original para que la siguiente fila sea para el siguiente producto
        }
        return originalRowIndex; // devolvemos el valor actualizado de rowIndex
    }
}
