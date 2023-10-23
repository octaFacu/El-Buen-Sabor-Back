package com.example.demo.EstadisticasExcel.ReporteUsuario;

import com.example.demo.Entidades.Proyecciones.ProyeccionHistorialPedidoUsuario;
import com.example.demo.Entidades.Proyecciones.ProyeccionProductosDePedido;
import com.example.demo.EstadisticasExcel.ReporteUsuario.Estilos.EstilosRankingClientes;
import com.example.demo.Services.ClienteService;
import com.example.demo.Services.PedidoService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ReporteUsuarioParteHistorial {
    // generamos el historial para cada cliente específico
    private EstilosRankingClientes estilos = new EstilosRankingClientes();

    public void generarHistorialCliente(XSSFWorkbook workbook, long clientId, ClienteService servicioCliente, PedidoService servicioPedido) throws Exception {
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
            rowIndex = ProductosHistorial(hojaHistorialPedidos, rowIndex, (int) historialUsuario.getPedido_id(), servicioPedido);
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
    private int ProductosHistorial(XSSFSheet hojaHistorialPedidos, int rowIndex, int pedidoId, PedidoService servicioPedido) throws Exception {
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
