package com.example.demo.EstadisticasExcel;

import com.example.demo.Entidades.Proyecciones.ProyeccionRankingProductos;
import com.example.demo.EstadisticasExcel.Estilos.EstilosRankingProductos;
import com.example.demo.Repository.ProductoRepository;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ReporteExcelRankingProductos implements PosicionesCeldasExcelRankingProductos{

    @Autowired
    private ProductoRepository repository;
    private EstilosRankingProductos estilos = new EstilosRankingProductos();

    public XSSFWorkbook  generarReporteDeExcelRankingProductos() throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(); // crea el libro
        XSSFSheet hoja = workbook.createSheet("Ranking Productos");
        XSSFRow filaCabecera = hoja.createRow(1);


        XSSFCellStyle estiloCabecera = estilos.crearEstiloCabecera(workbook);

        XSSFCellStyle estiloDatos = estilos.crearEstiloDatos(workbook);

        CabeceraRankingComidas(hoja, filaCabecera, estiloCabecera);
        CabeceraRankingBebidas(hoja, filaCabecera, estiloCabecera);

        llenarDatosProductosComidas(hoja, estiloDatos);
        llenarDatosProductosBebidas(hoja, estiloDatos);

        return workbook;
    }

    private void CabeceraRankingComidas(XSSFSheet hoja, XSSFRow filaCabecera, XSSFCellStyle estiloCabecera) {
        filaCabecera.createCell(PosicionesCeldasExcelRankingProductos.COMIDA_PRODUCTO).setCellValue("Productos");
        filaCabecera.createCell(PosicionesCeldasExcelRankingProductos.COMIDAS).setCellValue("Comida");
        filaCabecera.createCell(PosicionesCeldasExcelRankingProductos.COMIDA_CATEGORIA).setCellValue("Categoria");
        filaCabecera.createCell(PosicionesCeldasExcelRankingProductos.COMIDA_VENDIDO_ACTUALMENTE).setCellValue("Vendido Actualmente");
        filaCabecera.createCell(PosicionesCeldasExcelRankingProductos.COMIDA_TOTAL_VENDIDO).setCellValue("Total Vendido");
        for (int i = 0; i <= PosicionesCeldasExcelRankingProductos.COMIDA_TOTAL_VENDIDO; i++) {
            XSSFCell celdaCabecera = filaCabecera.getCell(i);
            celdaCabecera.setCellStyle(estiloCabecera);
        }
    }

    private void CabeceraRankingBebidas(XSSFSheet hoja, XSSFRow filaCabecera, XSSFCellStyle estiloCabecera) {

        filaCabecera.createCell(PosicionesCeldasExcelRankingProductos.BEBIDA_PRODUCTO).setCellValue("Productos");
        filaCabecera.createCell(PosicionesCeldasExcelRankingProductos.BEBIDA).setCellValue("Bebida");
        filaCabecera.createCell(PosicionesCeldasExcelRankingProductos.BEBIDA_CATEGORIA).setCellValue("Categoria");
        filaCabecera.createCell(PosicionesCeldasExcelRankingProductos.BEBIDA_VENDIDO_ACTUALMENTE).setCellValue("Vendido Actualmente");
        filaCabecera.createCell(PosicionesCeldasExcelRankingProductos.BEBIDA_TOTAL_VENDIDO).setCellValue("Total Vendido");

        for (int i = 6; i <= PosicionesCeldasExcelRankingProductos.BEBIDA_TOTAL_VENDIDO; i++) {
            XSSFCell celdaCabecera = filaCabecera.getCell(i);
            celdaCabecera.setCellStyle(estiloCabecera);
        }
    }

    private void llenarDatosProductosComidas(XSSFSheet hoja, XSSFCellStyle estiloDatos) {
        List<ProyeccionRankingProductos> rankingComidas = repository.rankingProductosComida(null, null, "DESC");
        int contador = 2;

        for (ProyeccionRankingProductos comidas : rankingComidas) {
            XSSFRow filaDatosComidas = hoja.createRow(contador);
            filaDatosComidas.createCell(PosicionesCeldasExcelRankingProductos.COMIDAS).setCellValue(comidas.getDenominacion());
            filaDatosComidas.createCell(PosicionesCeldasExcelRankingProductos.COMIDA_CATEGORIA).setCellValue(comidas.getDenominacion_categoria());

            XSSFCell celdaEstado = filaDatosComidas.createCell(PosicionesCeldasExcelRankingProductos.COMIDA_VENDIDO_ACTUALMENTE);
            celdaEstado.setCellValue(comidas.getActivo() ? "Activo" : "Inactivo");

            celdaEstado.setCellStyle(estiloDatos);

            if (celdaEstado.getColumnIndex() == PosicionesCeldasExcelRankingProductos.COMIDA_VENDIDO_ACTUALMENTE) {
                XSSFCellStyle estiloCelda = hoja.getWorkbook().createCellStyle();
                XSSFFont font = hoja.getWorkbook().createFont();
                if (comidas.getActivo()) {
                    font.setColor(IndexedColors.GREEN.getIndex());
                } else {
                    font.setColor(IndexedColors.RED.getIndex());
                }
                estiloCelda.cloneStyleFrom(estiloDatos); // Clonar el estilo general
                estiloCelda.setFont(font);
                celdaEstado.setCellStyle(estiloCelda);
            }

            filaDatosComidas.createCell(PosicionesCeldasExcelRankingProductos.COMIDA_TOTAL_VENDIDO).setCellValue(comidas.getCantidad());
            contador++;

            for (int j = 1; j <= PosicionesCeldasExcelRankingProductos.COMIDA_TOTAL_VENDIDO; j++) {
                if(j == PosicionesCeldasExcelRankingProductos.COMIDA_VENDIDO_ACTUALMENTE){
                    hoja.autoSizeColumn(j);
                }else{
                    XSSFCell celdaDatos = filaDatosComidas.getCell(j);
                    celdaDatos.setCellStyle(estiloDatos); // Aplicar estiloDatos a las demás celdas
                    hoja.autoSizeColumn(j);
                }
            }
        }
    }


    private void llenarDatosProductosBebidas(XSSFSheet hoja, XSSFCellStyle estiloDatos) {
        List<ProyeccionRankingProductos> rankingBebidas = repository.rankingProductosBebidas(null,null, "ASC");
        int contador = 2;

        for (ProyeccionRankingProductos bebidas : rankingBebidas) {
            XSSFRow filaDatosComidas = hoja.getRow(contador);
            filaDatosComidas.createCell(PosicionesCeldasExcelRankingProductos.BEBIDA).setCellValue(bebidas.getDenominacion());
            filaDatosComidas.createCell(PosicionesCeldasExcelRankingProductos.BEBIDA_CATEGORIA).setCellValue(bebidas.getDenominacion_categoria());

            XSSFCell celdaEstado = filaDatosComidas.createCell(PosicionesCeldasExcelRankingProductos.BEBIDA_VENDIDO_ACTUALMENTE);
            celdaEstado.setCellValue(bebidas.getActivo() ? "Activo" : "Inactivo");

            celdaEstado.setCellStyle(estiloDatos);

            if (celdaEstado.getColumnIndex() == PosicionesCeldasExcelRankingProductos.BEBIDA_VENDIDO_ACTUALMENTE) {
                XSSFCellStyle estiloCelda = hoja.getWorkbook().createCellStyle();
                XSSFFont font = hoja.getWorkbook().createFont();
                if (bebidas.getActivo()) {
                    font.setColor(IndexedColors.GREEN.getIndex());
                } else {
                    font.setColor(IndexedColors.RED.getIndex());
                }
                estiloCelda.cloneStyleFrom(estiloDatos); // Clonar el estilo general
                estiloCelda.setFont(font);
                celdaEstado.setCellStyle(estiloCelda);
            }

            filaDatosComidas.createCell(PosicionesCeldasExcelRankingProductos.BEBIDA_TOTAL_VENDIDO).setCellValue(bebidas.getCantidad());
            contador++;

            for (int j = 7; j <= PosicionesCeldasExcelRankingProductos.BEBIDA_TOTAL_VENDIDO; j++) {
                if(j == PosicionesCeldasExcelRankingProductos.BEBIDA_VENDIDO_ACTUALMENTE){
                    hoja.autoSizeColumn(j);
                }else{
                    XSSFCell celdaDatos = filaDatosComidas.getCell(j);
                    celdaDatos.setCellStyle(estiloDatos); // Aplicar estiloDatos a las demás celdas
                    hoja.autoSizeColumn(j);
                }
            }
        }
    }
}