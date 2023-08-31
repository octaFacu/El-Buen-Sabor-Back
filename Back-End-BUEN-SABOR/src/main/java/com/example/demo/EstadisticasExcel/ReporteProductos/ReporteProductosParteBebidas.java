package com.example.demo.EstadisticasExcel.ReporteProductos;

import com.example.demo.Entidades.Proyecciones.ProyeccionRankingProductos;
import com.example.demo.Repository.ProductoRepository;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.*;

import java.util.List;

public class ReporteProductosParteBebidas {

    public void CabeceraRankingBebidas(XSSFSheet hoja, XSSFRow filaCabecera, XSSFCellStyle estiloCabecera) {

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


    public void llenarDatosProductosBebidas(XSSFSheet hoja, XSSFCellStyle estiloDatos, ProductoRepository repository) {
        List<ProyeccionRankingProductos> rankingBebidas = repository.rankingProductosBebidas(null,null, "ASC");
        int contador = 2;

        for (ProyeccionRankingProductos bebidas : rankingBebidas) {
            XSSFRow filaDatosComidas = hoja.getRow(contador);
            filaDatosComidas.createCell(PosicionesCeldasExcelRankingProductos.BEBIDA).setCellValue(bebidas.getDenominacion());
            filaDatosComidas.createCell(PosicionesCeldasExcelRankingProductos.BEBIDA_CATEGORIA).setCellValue(bebidas.getDenominacion_categoria());

            XSSFCell celdaEstado = filaDatosComidas.createCell(PosicionesCeldasExcelRankingProductos.BEBIDA_VENDIDO_ACTUALMENTE);
            celdaEstado.setCellValue(bebidas.getActivo() ? "Activo" : "Inactivo");

            celdaEstado.setCellStyle(estiloDatos);
            agregarColorActivoInactivo(hoja,bebidas,celdaEstado,estiloDatos);


            filaDatosComidas.createCell(PosicionesCeldasExcelRankingProductos.BEBIDA_TOTAL_VENDIDO).setCellValue(bebidas.getCantidad());
            contador++;
            aplicarEstilosCeldas(hoja, filaDatosComidas,estiloDatos);

        }
    }
    public void agregarColorActivoInactivo(XSSFSheet hoja, ProyeccionRankingProductos bebidas,  XSSFCell celdaEstado, XSSFCellStyle estiloDatos){
        if (celdaEstado.getColumnIndex() == PosicionesCeldasExcelRankingProductos.BEBIDA_VENDIDO_ACTUALMENTE) {
            XSSFCellStyle estiloCelda = hoja.getWorkbook().createCellStyle();
            XSSFFont font = hoja.getWorkbook().createFont();
            if (bebidas.getActivo()) {
                font.setColor(IndexedColors.GREEN.getIndex());
            } else {
                font.setColor(IndexedColors.RED.getIndex());
            }
            estiloCelda.cloneStyleFrom(estiloDatos);
            estiloCelda.setFont(font);
            celdaEstado.setCellStyle(estiloCelda);
        }
    }
    public void aplicarEstilosCeldas(Sheet hoja, XSSFRow filaDatosComidas, XSSFCellStyle estiloDatos){
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
