package com.example.demo.EstadisticasExcel.ReporteUsuario;

import com.example.demo.Entidades.Proyecciones.ProyeccionEstadisticaClienteTotalPedidos;
import com.example.demo.EstadisticasExcel.ReporteUsuario.Estilos.EstilosRankingClientes;
import com.example.demo.Services.ClienteService;
import com.example.demo.Services.PedidoService;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public class ReporteUsuarioPartePaginaPrincipal {

    private EstilosRankingClientes estilos = new EstilosRankingClientes();
    private ReporteUsuarioParteHistorial parteHistorial = new ReporteUsuarioParteHistorial();
    protected void CabeceraRanking(XSSFSheet hoja) {
        XSSFRow filaCabecera = hoja.createRow(0);
        filaCabecera.createCell(PosicionesCeldasExcel.ID_CLIENTE).setCellValue("id cliente");
        filaCabecera.createCell(PosicionesCeldasExcel.NOMBRE).setCellValue("Nombre");
        filaCabecera.createCell(PosicionesCeldasExcel.PEDIDOS).setCellValue("Pedidos");
        filaCabecera.createCell(PosicionesCeldasExcel.IMPORTE_TOTAL).setCellValue("Importe Total");
        filaCabecera.createCell(PosicionesCeldasExcel.VER_HISTORIAL).setCellValue("Ver Historial");
    }

    // Crea las filas y llena los datos de clientes en la hoja de ranking
    public void ColumnasRanking(XSSFSheet hoja, XSSFWorkbook workbook, ClienteService servicioCliente, PedidoService servicioPedido) throws Exception {
        List<ProyeccionEstadisticaClienteTotalPedidos> rankingClientes = servicioCliente.ProyeccionEstadisticaClienteTotalPedidosSinPages();

        for (int i = 0; i < rankingClientes.size(); i++) {
            XSSFRow filaDatos = hoja.createRow(i + 1); // Crear una nueva fila
            llenarDatosCliente(filaDatos, rankingClientes.get(i)); // llenamos la fila con los datos del cliente
            parteHistorial.generarHistorialCliente(workbook, rankingClientes.get(i).getId_cliente(), servicioCliente, servicioPedido); // Generar la hoja de historial para el cliente

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
}
