package com.example.demo.EstadisticasExcel.ReporteProductos;

import com.example.demo.Entidades.Proyecciones.ProyeccionRankingProductos;
import com.example.demo.Repository.ProductoRepository;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.*;

import java.util.List;

public class ReporteProductosParteComidas {

    public void CabeceraRankingComidas(XSSFSheet hoja, XSSFRow filaCabecera, XSSFCellStyle estiloCabecera) {
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

    public void llenarDatosProductosComidas(XSSFSheet hoja, XSSFCellStyle estiloDatos, ProductoRepository repository) {
        List<ProyeccionRankingProductos> rankingComidas = repository.rankingProductosComida(null, null, "DESC");
        int contador = 2;

        for (ProyeccionRankingProductos comidas : rankingComidas) {
            XSSFRow filaDatosComidas = hoja.createRow(contador);
            filaDatosComidas.createCell(PosicionesCeldasExcelRankingProductos.COMIDAS).setCellValue(comidas.getDenominacion());
            filaDatosComidas.createCell(PosicionesCeldasExcelRankingProductos.COMIDA_CATEGORIA).setCellValue(comidas.getDenominacion_categoria());

            XSSFCell celdaEstado = filaDatosComidas.createCell(PosicionesCeldasExcelRankingProductos.COMIDA_VENDIDO_ACTUALMENTE);
            celdaEstado.setCellValue(comidas.getActivo() ? "Activo" : "Inactivo");

            celdaEstado.setCellStyle(estiloDatos);

            agregarColorActivoInactivo(celdaEstado.getColumnIndex(), hoja, comidas, celdaEstado, estiloDatos);

            filaDatosComidas.createCell(PosicionesCeldasExcelRankingProductos.COMIDA_TOTAL_VENDIDO).setCellValue(comidas.getCantidad());
            contador++;
            aplicarEstilosCeldas(hoja, filaDatosComidas, estiloDatos);
        }
    }

    public void aplicarEstilosCeldas(Sheet hoja, XSSFRow filaDatosComidas, XSSFCellStyle estiloDatos){
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

    public void agregarColorActivoInactivo(int indice, XSSFSheet hoja, ProyeccionRankingProductos comidas,  XSSFCell celdaEstado, XSSFCellStyle estiloDatos){
        if (indice == PosicionesCeldasExcelRankingProductos.COMIDA_VENDIDO_ACTUALMENTE) {
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
    }
}
