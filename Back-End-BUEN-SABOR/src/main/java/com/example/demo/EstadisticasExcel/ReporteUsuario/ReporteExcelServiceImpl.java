package com.example.demo.EstadisticasExcel.ReporteUsuario;


import com.example.demo.Services.ClienteService;
import com.example.demo.Services.PedidoService;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ReporteExcelServiceImpl implements PosicionesCeldasExcel {

    @Autowired
    private ClienteService servicioCliente;

    @Autowired
    private PedidoService servicioPedido;

    private ReporteUsuarioPartePaginaPrincipal paginaPrincipal = new ReporteUsuarioPartePaginaPrincipal();

    // este metodo es el que se encarga de crear el libro y cerrarlo una ves creado
    public XSSFWorkbook  generarReporteDeExcel() throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(); // crea el libro
        XSSFSheet hoja = workbook.createSheet("Ranking de clientes"); // crea la hoja principal

        paginaPrincipal.CabeceraRanking(hoja);
        paginaPrincipal.ColumnasRanking(hoja, workbook, servicioCliente, servicioPedido);

        return workbook;
    }

}
