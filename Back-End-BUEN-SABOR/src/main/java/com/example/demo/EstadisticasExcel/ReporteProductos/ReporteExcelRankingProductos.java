package com.example.demo.EstadisticasExcel.ReporteProductos;

import com.example.demo.EstadisticasExcel.ReporteProductos.Estilos.EstilosRankingProductos;
import com.example.demo.Repository.ProductoRepository;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ReporteExcelRankingProductos implements PosicionesCeldasExcelRankingProductos {

    @Autowired
    private ProductoRepository repository;
    private EstilosRankingProductos estilos = new EstilosRankingProductos();

    private ReporteProductosParteBebidas parteBebidas = new ReporteProductosParteBebidas();
    private ReporteProductosParteComidas parteComidas = new ReporteProductosParteComidas();

    public XSSFWorkbook  generarReporteDeExcelRankingProductos() throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(); // crea el libro
        XSSFSheet hoja = workbook.createSheet("Ranking Productos");
        XSSFRow filaCabecera = hoja.createRow(1);


        XSSFCellStyle estiloCabecera = estilos.crearEstiloCabecera(workbook);

        XSSFCellStyle estiloDatos = estilos.crearEstiloDatos(workbook);

        parteComidas.CabeceraRankingComidas(hoja, filaCabecera, estiloCabecera);
        parteBebidas.CabeceraRankingBebidas(hoja, filaCabecera, estiloCabecera);

        parteComidas.llenarDatosProductosComidas(hoja, estiloDatos, repository);
        parteBebidas.llenarDatosProductosBebidas(hoja, estiloDatos, repository);

        return workbook;
    }

}